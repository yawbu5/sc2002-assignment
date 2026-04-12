package systems.states.battle;

import systems.BattleEngine;
import systems.states.GameState;
import ui.GameView;

/**
 * Prepares all required systems and data and prepare for 1st turn
 */
public class InitialiseState implements GameState {
    @Override
    public void onEnter(BattleEngine engine, GameView view) {

    }

    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {

        return new ResolveTurnState();
    }

    @Override
    public void onExit(BattleEngine engine, GameView view) {

    }
}
