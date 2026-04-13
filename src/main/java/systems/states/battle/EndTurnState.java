package systems.states.battle;

import systems.BattleEngine;
import systems.states.GameState;
import ui.GameView;

/**
 * Responsibility: Check win/lose conditions, then process accordingly / move to next turn
 */
public class EndTurnState implements GameState {
    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        return null;
    }
}
