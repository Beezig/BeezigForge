/*
 * This file is part of BeezigForge.
 *
 * BeezigForge is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeezigForge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeezigForge.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.forge.gui.settings.speedrun;

import com.google.common.collect.ImmutableMap;
import eu.beezig.core.api.SettingInfo;
import eu.beezig.forge.ForgeMessage;
import eu.beezig.forge.api.BeezigAPI;
import eu.beezig.forge.gui.settings.GuiBeezigSettings;
import eu.beezig.forge.gui.settings.GuiColorPicker;
import eu.beezig.forge.gui.settings.GuiDualList;
import eu.beezig.forge.gui.settings.SettingEntry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public class GuiSpeedrunSettings extends GuiBeezigSettings {
    private final List<SettingInfo> settingInfos;
    private final Map<String, SettingInfo> indexes = new HashMap<>();

    public GuiSpeedrunSettings(List<SettingInfo> settings) {
        super(null, null);
        this.settingInfos = new ArrayList<>(settings);
        for(SettingInfo settingInfo : settings) {
            indexes.put(settingInfo.key, settingInfo);
        }
        populate(settings);
    }

    @Override
    public void onGuiClosed() {
        BeezigAPI.saveSpeedrunConfig(settingInfos);
    }

    private void populate(List<SettingInfo> settings) {
        settings.removeIf(s -> {
            if(s.key.startsWith("COLOR_") && s.value instanceof Integer) {
                this.settings.addEntry(new SettingEntry.ColorEntry(this, s.name, s.key, s.desc, (int) s.value, GuiColorPicker.ColorMode.ARGB));
                return true;
            } else if(s.value instanceof String[]) {
                List<String> value = new ArrayList<>(Arrays.asList((String[]) s.value));
                if(value.stream().anyMatch(s1 -> s1.startsWith("Model: "))) {
                    s.value = value.stream().filter(s1 -> !s1.startsWith("Model: ")).toArray(String[]::new);
                    this.settings.addEntry(new DualListEntry(this, s.name, s.key, s.desc, value));
                    return true;
                }
            }
            return false;
        });
        this.settings.populate(ImmutableMap.of("", settings));
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void saveEntry(SettingEntry entry, Object value) {
        indexes.get(entry.getKey()).value = value;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent evt) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    private static class DualListEntry extends SettingEntry {
        private final List<StringEntry> model = new ArrayList<>();
        private final List<StringEntry> result = new ArrayList<>();

        protected DualListEntry(GuiBeezigSettings parentScreen, String name, String key, String desc, List<String> input) {
            super(parentScreen, name, key, ForgeMessage.translate("gui.settings.action.configure"), desc, input);
            for(String s : input) {
                if(s.startsWith("Model: ")) {
                    String id = s.replace("Model: ", "");
                    model.add(new StringEntry(id, ForgeMessage.translate("speedrun.module." + id)));
                } else result.add(new StringEntry(s, ForgeMessage.translate("speedrun.module." + s)));
            }
        }

        @Override
        protected String formatValue() {
            return ForgeMessage.translate("gui.settings.action.configure");
        }

        @Override
        protected int valueColor() {
            return 0xffffffff;
        }

        @Override
        protected void onButtonClick() {
            Minecraft.getMinecraft().displayGuiScreen(new GuiDualList<>(parentScreen, model, result,
                    () -> parentScreen.saveEntry(this, result.stream().map(e -> e.id).toArray(String[]::new))));
        }

        private static class StringEntry extends GuiDualList.DualListEntry {
            private final String text;
            private final String id;

            public StringEntry(String id, String text) {
                this.text = text;
                this.id = id;
            }

            @Override
            public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
                Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, 0xffffffff);
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                StringEntry that = (StringEntry) o;
                return id.equals(that.id);
            }

            @Override
            public int hashCode() {
                return Objects.hash(id);
            }
        }
    }
}
