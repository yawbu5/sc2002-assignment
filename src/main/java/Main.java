import data.GameResources;
import systems.BattleEngine;
import view.console.ConsoleView;


public class Main {
    public static void main(String[] args) {
        GameResources db = new GameResources();
        BattleEngine engine = new BattleEngine(db);
        ConsoleView view = new ConsoleView();

        view.connectEngine(engine);

        engine.start();
    }
}
