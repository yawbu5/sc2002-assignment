package systems.states.battle;

import systems.BattleEngine;
import systems.states.GameState;
import ui.GameView;

/**
 * Responsibility: Determines turn order, manages game ticks.
 */
public class StartTurnState implements GameState {
    @Override
    public void onEnter(BattleEngine engine, GameView view) {

    }

    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        // Load abilities as known actions
        return null;
    }

    @Override
    public void onExit(BattleEngine engine, GameView view) {

    }
}
