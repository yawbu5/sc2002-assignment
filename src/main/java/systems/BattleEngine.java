package systems;

import data.GameResources;
import data.Wave;
import ui.GameView;

public class BattleEngine {
    private enum BATTLE_STATE {
        START,
        PLAYER_TURN,
        RESOLVE,
        ENEMY_TURN,
        END,
        RESULT
    }
    private GameResources db;
    private BATTLE_STATE bState;
    private EntityManager em;
    private src.gameCore.ActionManager am;
    private GameView view;
    private Wave difficulty;
    private Entity selectedPlayer;

    public BattleEngine(GameResources db) {
        this.db = db;
    }

    // TODO: prepare items

    /**
     * Game initialisation prompt
     */
    public void start() {
        if (this.view == null) {
            return;
        }

    }

    public void subscribe(GameView view) {
        this.view = view;
    }

    public String getStateString() {
        return this.bState.name();
    }
}
