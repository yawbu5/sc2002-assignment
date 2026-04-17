package observable;

import systems.states.battle.BattleData;

import java.util.List;

/**
 * Exposes battle-related messages
 */
public interface BattleObserver {
    void onLogAction(String msg);

    void onWaveSpawn(int waveNo);

    void onEntitySpawn(String entId);

    void onRoundStart(int roundCount);

    void onRoundEnd(int roundCount);

    // format: [, CURR_HP, MAX_HP, DEF, SPD] i.e., {0, 150, 250, 20, 30] -> Warrior (YOU) | HP: 150/250, DEF: 20, SPD: 30
    void onUpdateStats(List<Integer> stats);

    void onGameWin(BattleData data);

    void onGameLose(BattleData data);
}
