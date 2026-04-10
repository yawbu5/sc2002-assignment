package src;

import src.data.GameResources;
import src.view.ConsoleView;
import src.view.GameView;

public class Game {
    public static void main(String[] args) {
        GameResources db = new GameResources();
        BattleEngine engine = new BattleEngine(db);
        GameView view = new ConsoleView();

        engine.subscribe(view);
        view.connectEngine(engine);


        if (!engine.start()) {
            view.DisplayMessage("NOPE!");
            return;
        }

        view.DisplayMessage(engine.getState());
    }
}
