package tk.roccodev.beezig.forge.pointstag;

public enum Games {

    BED("total_points", "Points"),
    TIMV("total_points", "Karma"),
    SKY("total_points", "Points"),
    CAI("total_points", "Points"),
    DR("total_points", "Points"),
    GRAV("points", "Points"),
    HIDE("total_points", "Points"),
    LAB("total_points", "Atoms"),
    GNT("total_points", "Points"),
    GNTM("total_points", "Points"),
    MIMV("total_points", "Karma"),
    SGN("total_points", "Points"),
    BP("total_points", "Points");


    private String points, display;

    Games(String points, String display){
        this.points = points;
        this.display = display;
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
}
