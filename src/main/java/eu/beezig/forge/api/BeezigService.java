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

package eu.beezig.forge.api;

import eu.beezig.core.api.IBeezigService;
import eu.beezig.core.api.SettingInfo;
import eu.beezig.core.news.ForgeNewsEntry;
import eu.beezig.forge.api.command.BeezigCommandRegistry;
import eu.beezig.forge.config.ConfigurationManager;
import eu.beezig.forge.gui.autovote.AutovoteGui;
import eu.beezig.forge.gui.settings.GuiBeezigSettings;
import eu.beezig.forge.gui.settings.speedrun.GuiSpeedrunSettings;
import eu.beezig.forge.gui.welcome.WelcomeGui;
import eu.beezig.forge.modules.pointstag.PointsTagCache;
import eu.beezig.forge.modules.shuffle.ShuffleForgeListener;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class BeezigService implements IBeezigService {

    @Override
    public void registerUserIndicator(Function<UUID, Integer> callback) {
        BeezigAPI.userRoleFunc = callback;
    }

    @Override
    public void setOnHive(boolean update) {
        BeezigAPI.onHive = update;
    }

    @Override
    public void setCurrentGame(String game) {
        PointsTagCache.clear();
        BeezigAPI.currentGame = game;
    }

    @Override
    public void registerTitle(Function<Map.Entry<String, Integer>, String> callback) {
        BeezigAPI.titleFunc = callback;
    }

    @Override
    public void addCommands(List<Object> commands) {
        for(Object cmd : commands) {
            BeezigCommandRegistry.register(cmd);
        }
    }

    @Override
    public void registerFormatNumber(Function<Long, String> callback) {
        BeezigAPI.formatFunc = callback;
    }

    @Override
    public void registerTranslate(Function<String, String> callback) {
        BeezigAPI.translateFunc = callback;
    }

    @Override
    public void registerNormalizeMapName(Function<String, String> callback) {
        BeezigAPI.mapFunc = callback;
    }

    @Override
    public void loadConfig(File beezigDir) {
        try {
            ConfigurationManager.initAll(beezigDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void autovoteShuffle(List<String> favorites) {
        ShuffleForgeListener.mgr.attemptVote(favorites);
    }

    @Override
    public void displayWelcomeGui() {
        WelcomeGui gui = new WelcomeGui();
        Minecraft.getMinecraft().displayGuiScreen(gui);
        gui.advanceStep(0);
    }

    @Override
    public void openSettings(Map<String, List<SettingInfo>> settings) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiBeezigSettings(Minecraft.getMinecraft().currentScreen, settings));
    }

    @Override
    public void openAutovote(Map<String, List<String>> maps) {
        AutovoteGui gui = new AutovoteGui(0);
        gui.setMapData(maps);
        Minecraft.getMinecraft().displayGuiScreen(gui);
    }

    @Override
    public void openSpeedrunConfig(List<SettingInfo> settings) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiSpeedrunSettings(settings));
    }

    @Override
    public void registerGetRegion(Supplier<String> callback) {
        BeezigAPI.regionFunc = callback;
    }

    @Override
    public void registerTranslateFormat(Function<Pair<String, Object[]>, String> callback) {
        BeezigAPI.translFormatFunc = callback;
    }

    @Override
    public void registerBeezigDir(Supplier<File> callback) {
        BeezigAPI.beezigDirFunc = callback;
    }

    @Override
    public void registerGetSetting(Function<String, Object> callback) {
        BeezigAPI.getSettingFunc = callback;
    }

    @Override
    public void registerSetSetting(Consumer<Map.Entry<String, Object>> callback) {
        BeezigAPI.setSettingFunc = callback;
    }

    @Override
    public void registerGetOverrides(Function<UUID, Optional<Map<String, Object>>> callback) {
        BeezigAPI.getOverridesFunc = callback;
    }

    @Override
    public void registerSaveConfig(Runnable runnable) {
        BeezigAPI.saveConfig = runnable;
    }

    @Override
    public void registerSetSettingAsIs(Consumer<Map.Entry<String, Object>> callback) {
        BeezigAPI.setSettingAsIsFunc = callback;
    }

    @Override
    public void registerSetAutovoteMaps(Consumer<Pair<String, List<String>>> callback) {
        BeezigAPI.setAutovoteFunc = callback;
    }

    @Override
    public void registerGetLoadedNews(Function<String, Set<ForgeNewsEntry>> callback) {
        BeezigAPI.getNewsFunc = callback;
    }

    @Override
    public void registerGetForumsNews(Supplier<Set<ForgeNewsEntry>> callback) {
        BeezigAPI.forumsFunc = callback;
    }

    @Override
    public void registerHasDailyScores(Supplier<Boolean> callback) {
        BeezigAPI.dailyScoresFunc = callback;
    }

    @Override
    public void registerSendDailyExtensions(Consumer<List<Pair<String, String>>> callback) {
        BeezigAPI.sendDailyExtensionsFunc = callback;
    }

    @Override
    public void registerSaveSpeedrunConfig(Consumer<List<SettingInfo>> callback) {
        BeezigAPI.saveSpeedrunFunc = callback;
    }
}
