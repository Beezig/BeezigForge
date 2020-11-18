/*
 * This file is part of BeezigForge.
 *
 * BeezigForge is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeezigForge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeezigForge.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.forge.gui.settings;

import eu.beezig.forge.ForgeMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class GuiColorPicker extends GuiScreen {
    private final GuiScreen parentScreen;
    private final Consumer<Integer> callback;
    private final ColorMode mode;
    private ScaledResolution scaledResolution;

    private int color = 0xFF_FF_FF;
    private float alpha = 1f;

    // Color building
    private int red = 255, green = 255, blue = 255;

    // Components
    private final GuiTextField rgbInput;
    private GuiSlider redSlider, greenSlider, blueSlider;

    public GuiColorPicker(GuiScreen parentScreen, ColorMode mode, int defaultValue, Consumer<Integer> callback) {
        this(parentScreen, mode, callback);
        applyDefaults(defaultValue);
    }

    public GuiColorPicker(GuiScreen parentScreen, ColorMode mode, Consumer<Integer> callback) {
        this.callback = callback;
        this.mode = mode;
        this.parentScreen = parentScreen;
        rgbInput = new GuiTextField(6, Minecraft.getMinecraft().fontRendererObj, 0, 0, 150, 20);
    }

    @Override
    public void initGui() {
        scaledResolution = new ScaledResolution(mc);
        buttonList.add(makeSlider(1, width / 2 - 75, height / 2 - 70, 0.02f, alpha, v -> alpha = v,
                v -> ForgeMessage.translate("gui.settings.color.opacity", (v == 2 ? 0 : (int) (v * 100)) + "%")));
        buttonList.add(redSlider = makeSlider(3, width / 2 - 75, height / 2 - 40, 0f, red / 255f, v -> {
            red = (int) (v * 255);
            recalcColor();
        }, v -> ForgeMessage.translate("gui.settings.color.red", (int) (v * 255f))));
        buttonList.add(greenSlider = makeSlider(4, width / 2 - 75, height / 2 - 10, 0f,green / 255f, v -> {
            green = (int) (v * 255);
            recalcColor();
        }, v -> ForgeMessage.translate("gui.settings.color.green", (int) (v * 255f))));
        buttonList.add(blueSlider = makeSlider(5, width / 2 - 75, height / 2 + 20, 0f,blue / 255f, v -> {
            blue = (int) (v * 255);
            recalcColor();
        }, v -> ForgeMessage.translate("gui.settings.color.blue", (int) (v * 255))));
        buttonList.add(new GuiButton(2, width / 2 - 100, height / 2 + 100, 200, 20, I18n.format("gui.done")));
        rgbInput.xPosition = width / 2 - 75;
        rgbInput.yPosition = height / 2 + 60;
    }

    private void recalcColor() {
        color = new Color(red, green, blue).getRGB() & 0x00FFFFFF;
        rgbInput.setText(String.format("#%06X", color));
    }

    private GuiSlider makeSlider(int id, int x, int y, float min, float current, Consumer<Float> valueMapper, Function<Float, String> formatter) {
        return new GuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}

            @Override
            public void onTick(int sliderId, float value) {
                if(sliderId == id) valueMapper.accept(value);
            }

            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, id, x, y, "", min, 1f, current, ($, $$, value) -> formatter.apply(value));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 2) done();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawColorRect(width / 2 - 25, height / (7 * scaledResolution.getScaleFactor()));
        drawPreview(width / 2, height / (7 * scaledResolution.getScaleFactor()) + 60);
        rgbInput.drawTextBox();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        rgbInput.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        rgbInput.textboxKeyTyped(typedChar, keyCode);
        String text = rgbInput.getText().replace("#", "");
        try {
            Color color = new Color(Integer.parseInt(text, 16));
            red = color.getRed();
            blue = color.getBlue();
            green = color.getGreen();
            this.color = color.getRGB() & 0x00ffffff;
            updateSliders();
        } catch (NumberFormatException ignored) {}
    }

    private void updateSliders() {
        redSlider.func_175219_a(red / 255f);
        greenSlider.func_175219_a(green / 255f);
        blueSlider.func_175219_a(blue / 255f);
    }

    private void applyDefaults(int colorIn) {
        if(mode == ColorMode.ARGB) {
            int alpha = colorIn >>> 24;
            this.color = colorIn & 0x00FFFFFF;
            this.alpha = alpha / (float) 0xff;
        } else if(mode == ColorMode.RGBA) {
            int alpha = colorIn & 0x000000FF;
            this.color = (colorIn >>> 8) & 0x00FFFFFF;
            this.alpha = alpha / (float) 0xff;
        }
        rgbInput.setText(String.format("#%06X", color));
        Color temp = new Color(color);
        red = temp.getRed();
        green = temp.getGreen();
        blue = temp.getBlue();
    }

    private int getRenderColor() {
        return ((int) (alpha * 0xff) << 24) | this.color;
    }

    private void done() {
        Integer color = null;
        int alpha = (int) (this.alpha * 0xff);
        if(mode == ColorMode.ARGB) {
            color = (alpha << 24) | this.color;
        } else if(mode == ColorMode.RGBA) {
            color = (this.color << 8) | alpha;
        }
        callback.accept(color);
        mc.displayGuiScreen(parentScreen);
    }

    private void drawColorRect(int x, int y) {
        drawRect(x - 3, y - 3, x + 53, y + 53, 0xff000000);
        drawRect(x, y, x + 50, y + 50, getRenderColor());
    }

    private void drawPreview(int x, int y) {
        String preview = ForgeMessage.translate("gui.settings.color.preview");
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 1);
        GlStateManager.scale(1.5, 1.5, 1.5);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        drawCenteredString(fontRendererObj, preview, 0, 0, getRenderColor());
        GlStateManager.popMatrix();
    }

    public enum ColorMode {
        RGBA,
        ARGB
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent evt) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }
}
