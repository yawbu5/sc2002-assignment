package src;

import src.data.GameResources;
import src.view.ConsoleView;
import src.view.GameView;

public class Game {
    public static void main(String[] args) {
        BattleEngine engine = new BattleEngine();
        GameView view = new ConsoleView();

        engine.subscribe(view);
        view.connectEngine(engine);

        GameResources db = new GameResources();
        engine.loadData(db);

        String difficulty, character;
        view.DisplayMessage("Select a difficulty");
        for (int i = 0; i < db.waves.size(); i++) {
            view.DisplayMessage((i + 1) + ": " + db.waves.get(i).name);
        }
        difficulty = db.waves.get(Integer.parseInt(view.PromptInput(">")) - 1).name;

        view.DisplayMessage("Select a character");
        for (int i = 0; i < db.entities.size(); i++) {
            view.DisplayMessage((i + 1) + ": " + db.entities.get(i).name);
        }
        character = db.entities.get(Integer.parseInt(view.PromptInput(">")) - 1).name;

        if (!engine.start(difficulty, character)) {
            view.DisplayMessage("ERROR: INVALID SETTINGS");
            return;
        }

        // TODO: prepare items

        view.DisplayMessage(engine.getState());
    }
}
