package src;

import src.data.GameResources;
import src.ui.ConsoleView;
import src.ui.GameView;

public class Main {
    public static void main(String[] args) {
        GameResources db = new GameResources();
        BattleEngine engine = new BattleEngine(db);
        GameView view = new ConsoleView();

        engine.subscribe(view);
        view.connectEngine(engine);
        engine.start();
    }
}
