package observable;

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

    void onUpdateStats(int id, String type, String name, int currHp, int maxHp, int def, int spd, int atk);

    void onUpdateInventory(List<String> items);

    void onEffectExpired(String effect, String target);

    void onEffectApplied(String effect, String caster, String target, int duration);

    void onGameStart();

    void onGameWin(int roundCount);

    void onGameLose(int roundCount);
}
