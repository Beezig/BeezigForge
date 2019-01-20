package tk.roccodev.beezig.forge.gui.briefing.recentgames.csv;

public class ValueEntries {

    public static String getValue(String[] data, LoggingGame game) {
        switch(game) {

            case bp:
                return safeValue(data, 0, "Points: ");
            case dr:
                return safeValue(data, 6, "Time: ");
            case cai:
                return safeValue(data, 0, "Points: ");
            case gnt:
                return null;
            case sky:
                return safeValue(data, 0, "Points: ") + " / " + safeValue(data, 2, "Kills: ");
            case hide:
                return safeValue(data, 2, "Kills: ");
            case timv:
                return safeValue(data, 0, "Role: ") + " / " + safeValue(data, 1, "Karma: ");
            case bedwars:
                return safeValue(data, 0, "Points: ") + " / " + safeValue(data, 3, "Kills: ") + " / "
                        + safeValue(data, 4, "Deaths: ") + " / " + safeValue(data, 5, "Beds: ");

        }
        return null;
    }

    private static String safeValue(String[] in, int slot, String prefix) {
        if(slot < 0 || slot >= in.length) return null;
        return prefix + in[slot];
    }

}
