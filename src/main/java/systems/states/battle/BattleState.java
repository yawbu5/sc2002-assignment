package systems.states.battle;

import systems.BattleEngine;

public interface BattleState {
    BattleState transition(BattleData data, BattleEngine engine);
}
