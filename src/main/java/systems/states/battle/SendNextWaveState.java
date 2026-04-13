package systems.states.battle;

import systems.BattleEngine;
import systems.states.GameState;
import ui.GameView;

/**
 * If there is another wave, inform the player of the incoming wave and add the new wave into the entity manager.
 */
public class SendNextWaveState implements GameState {
    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        return new StartTurnState();
    }
}
