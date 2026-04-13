package systems.states;

import systems.BattleEngine;
import ui.GameView;

/**
 * GameState defines a certain unique phase,behaviour or action that is entered into
 * by either another state or the base game loop.
 * <p>
 * onUpdate MUST be defined, while onEnter and onExit are optional.
 * This behaviour should be reflected in the implemented code, where we may call those optional
 * states dynamically without necessarily expecting a result or change.
 */
public interface GameState {
    // default methods (onEnter, onExit) treated as optional
    default void onEnter(BattleEngine engine, GameView view) {
    }

    default void onExit(BattleEngine engine, GameView view) {
    }

    // MUST define an implementation for onUpdate
    GameState onUpdate(BattleEngine engine, GameView view);
}
