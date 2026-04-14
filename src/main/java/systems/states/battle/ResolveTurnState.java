package systems.states.battle;

import systems.BattleEngine;
import systems.states.GameState;

/**
 * Responsibility: Executes actions and other "concurrent" effects
 */
public class ResolveTurnState implements GameState {
    @Override
    public GameState onUpdate(BattleEngine engine) {
        return new EndTurnState();
    }
}
