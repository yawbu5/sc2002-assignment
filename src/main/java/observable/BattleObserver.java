package observable;

public interface BattleObserver {
    void OnLogAction(String msg);
    void OnWaveSpawn(int waveNo);
    void OnEntitySpawn(String entId);
}
