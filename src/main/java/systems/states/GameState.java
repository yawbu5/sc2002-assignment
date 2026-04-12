package systems.states;

import systems.BattleEngine;
import ui.GameView;

public interface GameState {
    void onEnter(BattleEngine engine, GameView view);
    default GameState onUpdate(BattleEngine engine, GameView view) {
        return null;
    }
    void onExit(BattleEngine engine, GameView view);
}
