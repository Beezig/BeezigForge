package tk.roccodev.beezig.forge.settings;

import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BeezigConfigElement implements IConfigElement {

    public String enumName, desc, briefDesc;
    public boolean enabled;

    public BeezigConfigElement(String enumName, String desc, boolean enabled, String briefDesc) {
        this.enabled = enabled;
        this.enumName = enumName;
        this.desc = desc;
        this.briefDesc = briefDesc;
    }

    @Override
    public boolean isProperty() {
        return true;
    }

    @Override
    public Class<? extends GuiConfigEntries.IConfigEntry> getConfigEntryClass() {
        return BeezigEntry.class;
    }

    @Override
    public Class<? extends GuiEditArrayEntries.IArrayEntry> getArrayEntryClass() {
        return null;
    }

    @Override
    public String getName() {
        return briefDesc;
    }

    @Override
    public String getQualifiedName() {
        return briefDesc;
    }

    @Override
    public String getLanguageKey() {
        return "";
    }

    @Override
    public String getComment() {
        return desc;
    }

    @Override
    public List<IConfigElement> getChildElements() {
        return new ArrayList<>();
    }

    @Override
    public ConfigGuiType getType() {
        return ConfigGuiType.BOOLEAN;
    }

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public boolean isListLengthFixed() {
        return false;
    }

    @Override
    public int getMaxListLength() {
        return 0;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public Object getDefault() {
        return true;
    }

    @Override
    public Object[] getDefaults() {
        return new Object[0];
    }

    @Override
    public void setToDefault() {

    }

    @Override
    public boolean requiresWorldRestart() {
        return false;
    }

    @Override
    public boolean showInGui() {
        return true;
    }

    @Override
    public boolean requiresMcRestart() {
        return false;
    }

    @Override
    public Object get() {
        return enabled + "";
    }

    @Override
    public Object[] getList() {
        return new Object[0];
    }

    @Override
    public void set(Object value) {
        this.enabled = (boolean) value;
    }

    @Override
    public void set(Object[] aVal) {

    }

    @Override
    public String[] getValidValues() {
        return new String[] {"true", "false"};
    }

    @Override
    public Object getMinValue() {
        return null;
    }

    @Override
    public Object getMaxValue() {
        return null;
    }

    @Override
    public Pattern getValidationPattern() {
        return null;
    }
}
