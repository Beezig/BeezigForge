package tk.roccodev.beezig.forge.pointstag;

public enum Games {

    BED("total_points", "Points", true),
    TIMV("total_points", "Karma", true),
    SKY("total_points", "Points", true),
    CAI("total_points", "Points", true),
    DR("total_points", "Points", true),
    GRAV("points", "Points", true),
    HIDE("total_points", "Points", true),
    LAB("total_points", "Atoms", true),
    GNT("total_points", "Points", true),
    GNTM("total_points", "Points", true),
    MIMV("total_points", "Karma", true),
    SGN("total_points", "Points", false),
    BP("total_points", "Points", true);


    private String points, display;
    private boolean supportsRanks;

    Games(String points, String display, boolean supportsRanks){
        this.points = points;
        this.display = display;
        this.supportsRanks = supportsRanks;
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
}
