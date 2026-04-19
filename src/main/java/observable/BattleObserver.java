package observable;

import systems.states.battle.BattleData;

import java.util.List;

/**
 * Exposes battle-related messages
 */
public interface BattleObserver {
    void onLogAction(String msg);

    void onWaveSpawn(int waveNo);

    void onRoundStart(int roundCount);

    void onDamage(int casterId, int targetId, String actionName, int damageDealt, int oldHp, boolean killed);

    void onHeal(int targetId, int healAmount);

    // format: [, CURR_HP, MAX_HP, DEF, SPD] i.e., {0, 150, 250, 20, 30] -> Warrior (YOU) | HP: 150/250, DEF: 20, SPD: 30
    void onUpdateStats(int id, String type, String name, int currHp, int maxHp, int def, int spd, int atk);

    void onUpdateInventory(List<String> items);

    void onEffectExpired(String effect, String target);

    void onEffectApplied(String effect, String caster, String target, int duration);

    void onGameStart(BattleData data);

    void onGameWin(BattleData data);

    void onGameLose(BattleData data);
}
