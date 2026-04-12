package systems.states.battle;

import systems.BattleEngine;
import systems.states.GameState;
import ui.GameView;

public class EndState implements GameState {
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
