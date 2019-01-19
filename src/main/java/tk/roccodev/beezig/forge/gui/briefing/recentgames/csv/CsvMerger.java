package tk.roccodev.beezig.forge.gui.briefing.recentgames.csv;

import tk.roccodev.beezig.forge.API;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CsvMerger {

    private List<GameData> recentGames = new ArrayList<>();

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

            recentGames.add(game);
        }
        s.close();
    }

    public CsvMerger() {
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
