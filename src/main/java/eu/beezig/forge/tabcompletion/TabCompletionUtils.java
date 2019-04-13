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

package eu.beezig.forge.tabcompletion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TabCompletionUtils {

    private static Collection<NetworkPlayerInfo> getLoadedPlayers() {
        return Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
    }

    public static List<String> matching(String[] args) {
        String arg = args[args.length - 1];
        List<String> tr = new ArrayList<>();
        if(!arg.isEmpty()) {
            for(NetworkPlayerInfo pl : getLoadedPlayers()) {
                if(pl.getGameProfile().getName().regionMatches(true, 0, arg, 0, arg.length()))
                    tr.add(pl.getGameProfile().getName());
            }
        }
        else {
            for(NetworkPlayerInfo pl : getLoadedPlayers()) {
                String name = pl.getGameProfile().getName();
                if(name.startsWith("§8")) continue;
                tr.add(name);
            }
        }
        return tr;
    }


}
