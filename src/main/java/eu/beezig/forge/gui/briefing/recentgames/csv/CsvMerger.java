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

package eu.beezig.forge.gui.briefing.recentgames.csv;

import eu.beezig.forge.API;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CsvMerger {

    private List<GameData> recentGames = new ArrayList<>();
    private int windowWidth;

    private void putIntoList(LoggingGame type) throws FileNotFoundException {
        Scanner s = new Scanner(new File(API.inst.getConfigPath() + "/" + type.name() + "/games.csv"));

        if(s.hasNextLine()) s.nextLine(); // Skip header

        while(s.hasNextLine()){
            String res = s.nextLine();
            String[] data = res.split(",");
            if(data.length == 0) continue;

            GameData game = new GameData();
            game.setGamemode(type);
            game.setSupportsWinning(type.canWin());
            game.setWon(type.won(data));
            game.setDate(type.getTimestamp(data));
            game.setGameId(type.getGameId(data));
            game.setMap(type.getMap(data));
            game.setMode(type.getMode(data));
            game.setValue(ValueEntries.getValue(data, type));
            game.initText(windowWidth);

            recentGames.add(game);
        }
        s.close();
    }

    public CsvMerger(int windowWidth) {
        this.windowWidth = windowWidth;
        for(LoggingGame game : LoggingGame.values()) {
            try {
                putIntoList(game);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        sort();
    }

    private void sort() {
        recentGames.sort((gameData, t1) -> t1.getTime() > gameData.getTime() ? 1 : -1);
    }


    public List<GameData> getRecentGames() {
        return recentGames;
    }
}
