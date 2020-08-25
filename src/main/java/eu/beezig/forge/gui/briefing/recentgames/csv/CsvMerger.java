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

import eu.beezig.forge.api.BeezigAPI;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvMerger {

    private List<GameData> recentGames = new ArrayList<>();
    private int windowWidth;

    private void putIntoList(LoggingGame type) throws IOException {
        ReversedLinesFileReader s = new ReversedLinesFileReader(new File(BeezigAPI.getBeezigDir(), type.name() + "/games.csv"));
        s.readLine();
        String res;
        while((res = s.readLine()) != null) {
            try {
                String[] data = res.split(",");
                if (data.length == 0) continue;

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
            } catch (Exception ex) {
                System.err.printf("Error occurred when loading a %s game%n", type.name());
                ex.printStackTrace();
            }
        }
        s.close();
    }

    public CsvMerger(int windowWidth) {
        this.windowWidth = windowWidth;
        for(LoggingGame game : LoggingGame.values()) {
            try {
                putIntoList(game);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<GameData> getRecentGames() {
        return recentGames;
    }
}
