package tk.roccodev.beezig.forge.gui.briefing.recentgames.csv;

public class ValueEntries {

    static final String rp = "beezigforge/gui/games/";

    public static String getValue(String[] data, LoggingGame game) {
        switch(game) {

            case bp:
                return safeValue(data, 0, "§7Points:§r ");
            case dr:
                return safeValue(data, 6, "§7Time:§r ");
            case cai:
                return safeValue(data, 0, "§7Points:§r ");
            case gnt:
                return null;
            case sky:
                return safeValue(data, 0, "§7Points:§r ") + " §r/ " + safeValue(data, 2, "§7Kills:§r ");
            case hide:
                return safeValue(data, 2, "§7Kills:§r ");
            case timv:
                return safeValue(data, 0, "§7Role:§r ") + " §r/ " + safeValue(data, 1, "§7Karma:§r ");
            case bedwars:
                return safeValue(data, 0, "§7Points:§r ") + " §r/ " + safeValue(data, 3, "§7Kills:§r ") + "§r / "
                        + safeValue(data, 4, "§7Deaths:§r ") + " §r/ " + safeValue(data, 5, "§7Beds:§r ");

        }
        return null;
    }

    private static String safeValue(String[] in, int slot, String prefix) {
        if(slot < 0 || slot >= in.length) return null;
        return prefix + in[slot];
    }

}
