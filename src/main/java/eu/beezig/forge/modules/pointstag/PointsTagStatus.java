package eu.beezig.forge.modules.pointstag;

public enum PointsTagStatus {

    UNKNOWN("?"),
    LOADING("?"),
    ERRORED("Error"),
    DONE(null);

    private String display;

    PointsTagStatus(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
