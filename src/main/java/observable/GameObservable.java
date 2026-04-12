package observable;

import systems.BattleEngine;

public interface GameObservable {
    void attach(GameObserver observer);
    void detach(GameObserver observer);
    void notifyObservers(BattleEngine engine);
}