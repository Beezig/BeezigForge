package tk.roccodev.beezig.forge.gui.autovote;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AutovoteMode {
    BED("BedWars"), SKY("SkyWars"),
    TIMV("Trouble in Mineville"), DR("DeathRun"),
    GRAV("Gravity"), HIDE("Hide and Seek"),
    GNT("SkyGiants"), SGN("Survival Games 2"),
    CAI("Cowboys and Indians"), MIMV("Murder in Mineville");


    private String display;

    AutovoteMode(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public static AutovoteMode get(int index) {
        return AutovoteMode.values()[index];
    }

    public static ArrayList<String> getDisplays() {
        return Stream.of(values()).map(AutovoteMode::getDisplay).collect(Collectors.toCollection(ArrayList::new));
    }
}
