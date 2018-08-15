package tk.roccodev.beezig.forge.enums;

public enum Modes {

    TIMV("Trouble in Mineville"),
    BED("BedWars"),
    CAI("Cowboys and Indians"),
    SKY("SkyWars"),
    DR("DeathRun"),
    HIDE("Hide and Seek"),

    SGN("Survival Games 2"),
    LAB("TheLab"),
    BP("BlockParty"),
    MIMV("Murder in Mineville"),
    GRAV("Gravity"),
    GNT("SkyGiants");


    private String display;

    Modes(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
