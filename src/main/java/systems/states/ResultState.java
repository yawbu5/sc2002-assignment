package systems.states;

import systems.BattleEngine;
import ui.GameView;

public class ResultState implements GameState{
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
