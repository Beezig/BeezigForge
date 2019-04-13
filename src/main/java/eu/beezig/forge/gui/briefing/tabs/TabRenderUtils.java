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

package eu.beezig.forge.gui.briefing.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TabRenderUtils extends Gui {

    private Minecraft minecraft;
    private ScaledResolution scaledResolution;
    private static TabRenderUtils instance = new TabRenderUtils();
    private boolean hasLimit = false;
    private int limit;

    private TabRenderUtils() {
        this.minecraft = Minecraft.getMinecraft();
        this.scaledResolution = new ScaledResolution(this.minecraft);
    }

    public TabRenderUtils(int limit) {
        this();
        hasLimit = true;
        this.limit = limit;
    }

    public List<String> listFormattedStringToWidth(final String text, final int width) {
        return this.minecraft.fontRendererObj.listFormattedStringToWidth(text, width);
    }

    public void drawCenteredString(final String text, final double x, final double y, final double size) {
        GL11.glPushMatrix();
        GL11.glScaled(size, size, size);
        this.drawCenteredString(text, x / size, y / size);
        GL11.glPopMatrix();
    }

    public void drawCenteredString(final String text, final double x, final double y) {
        this.drawString(text, x - this.getStringWidth(text) / 2, y);
    }

    public void drawString(final String text, final double x, final double y) {
        this.minecraft.fontRendererObj.drawString(text, (float)x, (float)y, 16777215, true);
    }

    public void drawString(final String text, final double x, final double y, final double size) {
        GL11.glPushMatrix();
        GL11.glScaled(size, size, size);
        this.drawString(text, x / size, y / size);
        GL11.glPopMatrix();
    }
    public void drawHorizontalLine(final int startX, final int endX, final int y, final int color) {
        this.drawHorizontalLine(startX, endX, y, color);
    }

    public void drawVerticalLine(final int x, final int startY, final int endY, final int color) {
        this.drawVerticalLine(x, startY, endY, color);
    }


    public void drawTexture(final double x, final double y, final double imageWidth, final double imageHeight, final double maxWidth, final double maxHeight) {
        this.drawTexture(x, y, imageWidth, imageHeight, maxWidth, maxHeight, 1.0f);
    }

    public void drawTexture(final double x, final double y, final double imageWidth, final double imageHeight, final double maxWidth, final double maxHeight, final float alpha) {
        GL11.glPushMatrix();
        final double sizeWidth = maxWidth / imageWidth;
        final double sizeHeight = maxHeight / imageHeight;
        GL11.glScaled(sizeWidth, sizeHeight, 0.0);
        if (alpha <= 1.0f) {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);
        }
        this.drawTexturedModalRect(x / sizeWidth, y / sizeHeight, x / sizeWidth + imageWidth, y / sizeHeight + imageHeight);
        if (alpha <= 1.0f) {
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
        }
        GL11.glPopMatrix();
    }

    public void drawRect(double left, double top, double right, double bottom, final int color) {
        if (left < right) {
            final double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            final double j = top;
            top = bottom;
            bottom = j;
        }

        if(hasLimit) {
            if(top < limit + 38)
                top = limit + 40;
            if(bottom < limit + 38)
                bottom = limit + 40;
        }
        final float f3 = (color >> 24 & 0xFF) / 255.0f;
        final float f4 = (color >> 16 & 0xFF) / 255.0f;
        final float f5 = (color >> 8 & 0xFF) / 255.0f;
        final float f6 = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f4, f5, f6, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void drawRectBorder(double left, double top, double right, double bottom, final int color, final double thickness) {
        this.drawRect(left + thickness, top, right - thickness, top + thickness, color);
        this.drawRect(right - thickness, top, right, bottom, color);
        this.drawRect(left + thickness, bottom - thickness, right - thickness, bottom, color);
        this.drawRect(left, top, left + thickness, bottom, color);
    }

    public void drawTexturedModalRect(final double left, final double top, final double right, final double bottom) {
        final double textureX = 0.0;
        final double textureY = 0.0;
        final double width = right - left;
        final double height = bottom - top;
        final float f = 0.00390625f;
        final float f2 = 0.00390625f;
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(left + 0.0, top + height, (double)this.zLevel).tex((double)((float)(textureX + 0.0) * f), (double)((float)(textureY + height) * f2)).endVertex();
        worldrenderer.pos(left + width, top + height, (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f2)).endVertex();
        worldrenderer.pos(left + width, top + 0.0, (double)this.zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0.0) * f2)).endVertex();
        worldrenderer.pos(left + 0.0, top + 0.0, (double)this.zLevel).tex((double)((float)(textureX + 0.0) * f), (double)((float)(textureY + 0.0) * f2)).endVertex();
        tessellator.draw();
    }


    public int getStringWidth(final String text) {
        return this.minecraft.fontRendererObj.getStringWidth(text);
    }

    public int getWidth() {
        return this.scaledResolution.getScaledWidth();
    }

    public int getHeight() {
        return this.scaledResolution.getScaledHeight();
    }

    public static TabRenderUtils getInstance() {
        return TabRenderUtils.instance;
    }

    public Minecraft getMinecraft() {
        return this.minecraft;
    }

}