package tk.roccodev.beezig.forge;

import java.text.DecimalFormat;

public class Log {


    public static String info = "§7▏ §aBeezig§7 ▏ §3";
    public static String error = "§7▏ §cBeezig§7 ▏ §c";
    public static String bar = "    §7§m                                                                                    ";
    private static final DecimalFormat bigintFormatter = new DecimalFormat("#,###");

    public static String df(long l) {
        return API.inst.getSettingValue("THOUSANDS_SEPARATOR") ? bigintFormatter.format(l) : Long.toString(l);
    }

}