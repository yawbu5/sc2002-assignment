package src;

import src.data.GameResources;
import src.view.ConsoleView;
import src.view.GameView;

public class Game {
    public enum GAME_STATE {
        MENU,
        LOADING,
        BATTLE,
        RESULT
    }

    public static void main(String[] args) {
        GAME_STATE gState = GAME_STATE.MENU;

        BattleEngine engine = new BattleEngine();
        GameView view = new ConsoleView();

        engine.subscribe(view);
        view.connectEngine(engine);

        GameResources db = new GameResources();
        engine.loadData(db);
        String difficulty, character;

        if (!engine.start(difficulty, character)) {
            view.DisplayMessage("ERROR: INVALID SETTINGS");
            return;
        }
    }
}
