package tk.roccodev.beezig.forge.gui.pointstag;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tk.roccodev.beezig.forge.config.pointstag.TagConfigManager;
import tk.roccodev.beezig.forge.pointstag.PointsTagCache;

import java.io.IOException;

public class TagSettingsGui extends GuiScreen {

    private Minecraft mc;

    public TagSettingsGui() {
        mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(3011, this.width / 2 - 155, this.height / 2 - 100 - 34, 150, 20,
                "Points Tag: " + (PointsTagCache.enabled ? "Enabled" : "Disabled")));

        this.buttonList.add(new GuiButton(2000, this.width / 2 + 5, this.height / 2 - 100 - 34, 150, 20,
                "Edit formatting..."));

        this.buttonList.add(new GuiButton(1603, this.width / 2 - 155, this.height / 2 -  83 + 22, 150, 20,
                "Show self: " +  (PointsTagCache.self ? "Enabled" : "Disabled")));

        this.buttonList.add(new GuiSlider(707, this.width / 2 + 5, this.height / 2 - 83 + 22,
                150, 20, "Tag Offset: ", "",
                -10d, 10d, 0d, false, true, slider -> {
                PointsTagCache.offset = slider.getValueInt() / 10d;
                TagConfigManager.offset.set(slider.getValueInt() / 10d);
        }));
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 3011:
                PointsTagCache.enabled = !PointsTagCache.enabled;
                TagConfigManager.enabled.set(PointsTagCache.enabled);
                button.displayString = "Points Tag: " + (PointsTagCache.enabled ? "Enabled" : "Disabled");
                break;
            case 1603:
                PointsTagCache.self = !PointsTagCache.self;
                TagConfigManager.showSelf.set(PointsTagCache.self);
                button.displayString = "Show self: " +  (PointsTagCache.self ? "Enabled" : "Disabled");
                break;
        }
    }

    @Override
    public void onGuiClosed() {
        TagConfigManager.save();
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        mc.displayGuiScreen(this);
    }
}
