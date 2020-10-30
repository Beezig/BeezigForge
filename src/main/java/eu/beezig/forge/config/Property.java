package eu.beezig.forge.config;

public class Property {
    private Object value;

    public Object getValue() {
        return value;
    }

    public Property(Object value) {
        this.value = value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getInt() {
        return (int) value;
    }

    public String getString() {
        return value.toString();
    }

    public boolean getBoolean() {
        return (boolean) value;
    }

    public double getDouble() {
        return (double) value;
    }
}
