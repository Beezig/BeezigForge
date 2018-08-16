package tk.roccodev.beezig.forge.listener;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.IConfigElement;
import tk.roccodev.beezig.forge.BeezigForgeMod;
import tk.roccodev.beezig.forge.gui.settings.GuiBeezigSettings;
import tk.roccodev.beezig.forge.settings.BeezigConfigElement;

import java.util.ArrayList;
import java.util.List;

public class ForgeListenerImpl {

    public void onLoad(String s, String s1) {
        System.out.println("Found Beezig " + s + " on 5zig " + s1);
        BeezigForgeMod.loaded = true;

        if(!s.equals(BeezigForgeMod.VERSION)) {
           BeezigForgeMod.versionUpdate = true;


        }

    }

    public void onDisplaySettingsGui(Object settings) {


        try {
            Object[] arr = (Object[]) settings.getClass().getField("array").get(settings);
            List<IConfigElement> elements = new ArrayList<>();
            for (Object o : arr) {
                String enumName = o.toString();
                String desc = (String) o.getClass().getMethod("getBriefDescription").invoke(o);
                String briefDesc = (String) o.getClass().getMethod("getBrieferDescription").invoke(o);
                boolean enabled = (boolean) o.getClass().getMethod("getValue").invoke(o);


                elements.add(new BeezigConfigElement(enumName, desc, enabled, briefDesc));
            }

            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Minecraft.getMinecraft().displayGuiScreen(new GuiBeezigSettings(null, elements));
            }).start();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }







}
