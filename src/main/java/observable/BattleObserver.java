package observable;

/**
 * Exposes battle-related messages
 */
public interface BattleObserver {
    void onLogAction(String msg);
    void onWaveSpawn(int waveNo);
    void onEntitySpawn(String entId);
    void onRoundStart(int roundCount);
    void onRoundEnd(int roundCount);
}
