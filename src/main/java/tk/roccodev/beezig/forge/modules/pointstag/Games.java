package tk.roccodev.beezig.forge.modules.pointstag;

public enum Games {

    BED("total_points", "Points", true, "BedWars"),
    TIMV("total_points", "Karma", true, "Trouble in Mineville"),
    SKY("total_points", "Points", true, "SkyWars"),
    CAI("total_points", "Points", true, "Cowboys and Indians"),
    DR("total_points", "Points", true, "DeathRun"),
    GRAV("points", "Points", true, "Gravity"),
    HIDE("total_points", "Points", true, "Hide and Seek"),
    LAB("total_points", "Atoms", true, "TheLab"),
    GNT("total_points", "Points", true, "SkyGiants"),
    GNTM("total_points", "Points", true, "SkyGiants:Mini"),
    MIMV("total_points", "Karma", true, "Murder in Mineville"),
    SGN("total_points", "Points", false, "Survival Games 2"),
    BP("total_points", "Points", true, "BlockParty"),
    SP("points", "Points", false, "Splegg"),
    HB("points", "Points", false, "The Herobrine"),
    DRAW("total_points", "Points", false, "DrawIt"),
    EE("points", "Points", false, "Explosive Eggs"),
    EF("points", "Points", false, "Electric Floor"),
    CR("total_points", "Points", false, "Cranked"),
    HERO("total_points", "Points", false, "SG:Heroes"),
    OITC("total_points", "Points", false, "One in the Chamber"),
    SPL("total_points", "Points", false, "Sploop"),
    MM("points", "Points", false, "MusicMasters"),
    SLAP("points", "Points", false, "Slaparoo"),
    RR("points", "Points", false, "RestaurantRush"),
    BD("total_points", "Points", false, "BatteryDash");

    private String points, display, commonName;
    private boolean supportsRanks;

    Games(String points, String display, boolean supportsRanks, String commonName){
        this.points = points;
        this.display = display;
        this.supportsRanks = supportsRanks;
        this.commonName = commonName;
    }

    public String getPoints() {
        return points;
    }

    public String getDisplay() {
        return display;
    }

    public static Games value(String search) {
        try {
            return Games.valueOf(search);
        }
        catch(Exception e) {
            return null;
        }
    }

    public boolean supportsRanks() {
        return supportsRanks;
    }

    public String getCommonName() {
        return commonName;
    }
}
