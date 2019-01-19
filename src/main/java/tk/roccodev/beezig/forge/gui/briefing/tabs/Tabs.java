package tk.roccodev.beezig.forge.gui.briefing.tabs;

import net.minecraft.util.ResourceLocation;
import tk.roccodev.beezig.forge.gui.briefing.tabs.items.BeezigNewsTab;
import tk.roccodev.beezig.forge.gui.briefing.tabs.items.HiveNewsTab;
import tk.roccodev.beezig.forge.gui.briefing.tabs.items.NewMapsTab;
import tk.roccodev.beezig.forge.gui.briefing.tabs.items.StaffChangesTab;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Tabs {

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static SimpleDateFormat formatHive = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

    static Tab[] tabs = new Tab[] {

            new HiveNewsTab(),
            new BeezigNewsTab(),
            new StaffChangesTab(),
            new NewMapsTab(),
            new Tab("Other", new ResourceLocation("beezigforge/gui/other.png"))

    };

    static Tab getTabByBox(int mouseX, int mouseY) {
        for(Tab tab : tabs) {
            if(tab.isHovered(mouseX, mouseY)) return tab;
        }
        return null;
    }
}
