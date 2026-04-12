import systems.BattleEngine;
import data.GameResources;
import ui.console.ConsoleView;
import ui.GameView;

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
