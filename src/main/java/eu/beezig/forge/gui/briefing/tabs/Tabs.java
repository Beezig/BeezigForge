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
