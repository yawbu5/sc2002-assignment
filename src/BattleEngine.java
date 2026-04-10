package src;

import src.data.GameResources;
import src.view.GameView;

import java.util.List;

public class BattleEngine {
    private enum BATTLE_STATE {
        LOADING,
        PLAYER_TURN,
        CHOOSING_ACTION,
        RESOLVING_ACTION,
        ENEMY_TURN,
        LOADED,
    }
    private Entity player;
    private List<Entity> entities;
    private Wave waves;
    private BATTLE_STATE bState;
    private GameView view;
    private GameResources db;

    public BattleEngine(GameResources db) {
        this.db = db;
        this.bState = BATTLE_STATE.LOADING;
    }

    // TODO: prepare items
    public boolean start() {
        String difficulty, character;
        while (true) {
            view.DisplayMessage("Select a difficulty");
            for (int i = 0; i < db.waves.size(); i++) {
                view.DisplayMessage((i + 1) + ": " + db.waves.get(i).name);
            }
            difficulty = db.waves.get(Integer.parseInt(view.GetUserInput("")) - 1).name;

            view.DisplayMessage("Select a character");
            for (int i = 0; i < 2; i++) {
                view.DisplayMessage((i + 1) + ": " + db.entities.get(i).name);
            }
            character = db.entities.get(Integer.parseInt(view.GetUserInput("")) - 1).name;

            if (!(difficulty == null || character == null)) {
                break;
            } else {
                view.DisplayMessage("ERROR: INVALID SETTINGS, TRY AGAIN");
            }
        }
        for (Wave w : db.waves) {
            if (w.name.equals(difficulty)) {
                this.waves = w;
            }
        }

        if (this.waves == null) return false;

        for (Entity e : db.entities) {
            if (e.name.equals(character)) {
                player = Entity.CreateEntity(e);
            }
        }

        if (this.player == null) return false;

        this.bState = BATTLE_STATE.LOADED;
        return true;
    }

    public void subscribe(GameView view) {
        this.view = view;
    }

    public void loadData(GameResources db) {
        this.db = db;
    }

    public String getState() {
        return this.bState.name();
    }

    private void resolveTurn() {
        if (this.bState != BATTLE_STATE.RESOLVING_ACTION) {

        }
        switch (this.bState) {
            case PLAYER_TURN:
                break;
            case ENEMY_TURN:
                break;
        }
    }
}
