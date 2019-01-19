package tk.roccodev.beezig.forge.gui.briefing.json.lergin;

public enum StaffChangeType {


    MODERATOR_REMOVE("§c", "Retiring Moderator"),
    MODERATOR_ADD("§c", "New Moderator"),
    SENIOR_MODERATOR_REMOVE("§4", "Retiring Sr. Moderator"),
    SENIOR_MODERATOR_ADD("§4", "New Sr. Moderator"),
    NECTAR_REMOVE("§3", "Retiring Builder"),
    NECTAR_ADD("§3", "New Builder"),
    DEVELOPER_REMOVE("§7", "Retiring Developer"),
    DEVELOPER_ADD("§7", "New Developer"),
    OWNER_REMOVE("§e", "Retiring Owner"),
    OWNER_ADD("§e", "New Owner");


    private String display;
    private String color;

    StaffChangeType(String color, String display) {
        this.display = display;
        this.color = color;
    }

    public String getDisplay() {
        return color + display;
    }

    public String getColor() {
        return color;
    }
}
