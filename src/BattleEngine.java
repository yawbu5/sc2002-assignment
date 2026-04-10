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
    private List<Ability> abilities;
    private Wave waves;
    private BATTLE_STATE bState;
    private GameView view;
    private GameResources db;

    public BattleEngine() {
        this.bState = BATTLE_STATE.LOADING;
    }

    public boolean start(String difficulty_name, String chosen_character) {
        for (Wave w : db.waves) {
            if (w.name.equals(difficulty_name)) {
                this.waves = w;
            }
        }

        if (this.waves == null) return false;

        for (Entity e : db.entities) {
            if (e.name.equals(chosen_character)) {
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
