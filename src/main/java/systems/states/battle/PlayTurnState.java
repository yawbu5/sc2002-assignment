package systems.states.battle;

import systems.BattleEngine;
import systems.states.GameState;
import ui.GameView;

/**
 * Responsibility: Wait for and validate user input, and handle selecting
 */
public class PlayTurnState implements GameState {
    @Override
    public void onEnter(BattleEngine engine, GameView view) {

    }

    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        return null;
    }

    @Override
    public void onExit(BattleEngine engine, GameView view) {

    }
}
