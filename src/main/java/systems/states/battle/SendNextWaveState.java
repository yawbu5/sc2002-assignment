package systems.states.battle;

import systems.BattleEngine;

/**
 * If there is another wave, inform the player of the incoming wave and add the new wave into the entity manager.
 */
public class SendNextWaveState implements BattleState {
    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        return new StartTurnState();
    }
}
