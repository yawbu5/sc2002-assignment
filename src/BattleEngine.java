package src;

import src.data.GameResources;
import src.data.Wave;
import src.gameCore.EntityManager;
import src.ui.GameView;

public class BattleEngine {
    private enum BATTLE_STATE {
        LOADING,
        AWAITING_PLAYER_INPUT,
        PLAYER_TURN,
        CHOOSING_ACTION,
        RESOLVING_ACTION,
        ENEMY_TURN,
        LOADED, ERROR,
    }
    private GameResources db;
    private BATTLE_STATE bState;
    private EntityManager em;
    private GameView view;
    private Wave difficulty;
    private Entity selectedPlayer;

    public BattleEngine(GameResources db) {
        this.db = db;
        this.bState = BATTLE_STATE.LOADING;
    }

    // TODO: prepare items

    /**
     * Game initialisation prompt
     */
    public void start() {
        if (this.view == null) {
            this.bState = BATTLE_STATE.ERROR;
            return;
        }

        this.bState = BATTLE_STATE.LOADED;
    }

    public void subscribe(GameView view) {
        this.view = view;
    }

    public String getStateString() {
        return this.bState.name();
    }
}
