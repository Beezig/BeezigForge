package eu.beezig.forge.gui.briefing.tabs;

import eu.beezig.forge.gui.briefing.tabs.items.*;
import net.minecraft.util.ResourceLocation;
import eu.beezig.forge.gui.briefing.recentgames.RecentGamesTab;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Tabs {

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static SimpleDateFormat formatHive = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

    static Tab[] tabs = new Tab[] {

            new HiveNewsTab(),
            new BeezigNewsTab(),
            new StaffChangesTab(),
            new NewMapsTab(),
            new RecentGamesTab(),
            new OtherTab()

    };

    static Tab getTabByBox(int mouseX, int mouseY) {
        for(Tab tab : tabs) {
            if(tab.isHovered(mouseX, mouseY)) return tab;
        }
        return null;
    }
}
