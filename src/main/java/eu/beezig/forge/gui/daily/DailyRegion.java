package eu.beezig.forge.gui.daily;

public class DailyRegion {
    private String id, name;

    public DailyRegion(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
