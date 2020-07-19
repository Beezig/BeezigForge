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
import eu.beezig.forge.api.command.BeezigCommandRegistry;
import eu.beezig.forge.config.ConfigurationManager;
import eu.beezig.forge.modules.pointstag.PointsTagCache;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

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
    public void registerTitle(Function<String, String> callback) {
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
    public void loadConfig(File beezigDir) {
        try {
            ConfigurationManager.initAll(beezigDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
