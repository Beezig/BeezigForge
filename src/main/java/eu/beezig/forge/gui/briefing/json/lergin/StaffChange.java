package eu.beezig.forge.gui.briefing.json.lergin;

import java.util.Date;

public class StaffChange {

    private Date date;
    private String name, uuid;
    private StaffChangeType type;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public StaffChangeType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = StaffChangeType.valueOf(type);
    }
}
