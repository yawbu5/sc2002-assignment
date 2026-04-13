package observable;

import systems.BattleEngine;

/**
 * Classes that implement this interface have the capability to send messages
 * and other kinds of data to subscribed listeners.
 */
public interface GameObserver {
    void onNotify(BattleEngine engine);
}
