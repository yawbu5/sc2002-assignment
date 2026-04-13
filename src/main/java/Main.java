import data.GameResources;
import systems.BattleEngine;
import ui.GameView;
import ui.console.ConsoleView;

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
