package systems.states;

import systems.BattleEngine;
import ui.GameView;

public class BattleState implements GameState {
    @Override
    public void onEnter(BattleEngine engine, GameView view) {

    }

    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        return GameState.super.onUpdate(engine, view);
    }

    @Override
    public void onExit(BattleEngine engine, GameView view) {

    }
}
