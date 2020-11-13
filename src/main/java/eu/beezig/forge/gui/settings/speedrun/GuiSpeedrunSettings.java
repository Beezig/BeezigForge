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
import eu.beezig.forge.api.BeezigAPI;
import eu.beezig.forge.gui.settings.GuiBeezigSettings;
import eu.beezig.forge.gui.settings.GuiColorPicker;
import eu.beezig.forge.gui.settings.SettingEntry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            }
            return false;
        });
        this.settings.populate(ImmutableMap.of("", settings));
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    protected void saveEntry(SettingEntry entry, Object value) {
        indexes.get(entry.getKey()).value = value;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent evt) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }
}
