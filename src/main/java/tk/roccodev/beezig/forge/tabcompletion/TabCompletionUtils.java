package tk.roccodev.beezig.forge.tabcompletion;

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
