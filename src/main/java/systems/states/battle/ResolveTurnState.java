package systems.states.battle;

import systems.BattleEngine;
import systems.states.GameState;

/**
 * Responsibility: Executes actions and other "concurrent" effects
 */
public class ResolveTurnState implements BattleState {
    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        return new EndTurnState();
    }
}
