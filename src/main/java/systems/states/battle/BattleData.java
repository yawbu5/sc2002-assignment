package systems.states.battle;

import data.EntityTemplate;
import data.Wave;
import systems.BattleEngine;
import systems.entities.Entity;
import systems.entities.EntityType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Holds (temporary) battle-specific context/data.
 * Makes it easier to restart the game
 */
public class BattleData {
    public int currentTurn = 0;     // on initial startTurn check, this number is current. However on post-checks we consider this to be currentTurn++
    public boolean requestRestart = false;
    public boolean requestExit = false;
    public boolean requestRestartSameSettings = false;
    private Wave difficulty;
    private int waveCount = 1;
    private int roundCounter = 1;
    private List<Integer> turnOrder;
    private List<String> playerInventory;   //  holder for restarts

    public void setDifficulty(Wave difficulty) {
        this.difficulty = difficulty;
    }

    public void incrementRoundCounter() {
        this.roundCounter++;
    }

    public boolean currentTurnIsPlayer() {
        return (getCurrentTurnEntityId() == 0);
    }

    public int getCurrentTurnEntityId() {
        return getTurnOrder().get(currentTurn - 1);
    }

    /**
     * Wave counts are one-based.
     */
    public int incrementWaveCount() {
        if (this.waveCount - 1 >= difficulty.waves.size()) {
            return -1;
        } else {
            this.waveCount++;
            return this.waveCount - 1;
        }
    }

    public int getWaveCount() {
        return this.waveCount;
    }

    public List<List<String>> getWaves() {
        return this.difficulty.waves;
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    public List<Integer> getTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(List<Integer> list) {
        this.turnOrder = list;
    }

    /**
     * Reset on game restarts
     */
    public void reset() {
        this.waveCount = 1;
        this.roundCounter = 1;
        this.currentTurn = 0;
    }

    public List<String> getPlayerInventory() {
        return this.playerInventory;
    }

    public void setPlayerInventory(List<String> inv) {
        this.playerInventory = inv;
    }

    public Wave getWave() {
        return this.difficulty;
    }

    public static void printTurnOrder(BattleData data, BattleEngine engine) {
        Map<Integer, String> uniqueEnemies = new HashMap<>();

        Entity player;
        if ((player = engine.getEntityManager().getEntity(0)) != null) {
            uniqueEnemies.putIfAbsent(player.getSpeed(), player.getName());
        }

        data.getWaves().stream()
                .flatMap(List::stream)
                .distinct()
                .forEach(name -> {
                    EntityTemplate entity = engine.retrieveDbEntity(name);
                    if (entity != null) {
                        uniqueEnemies.put(entity.speed, entity.name);
                    }
                });

        String order = uniqueEnemies.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .map(e -> String.format("%s (SPD %d)", e.getValue(), e.getKey()))
                .collect(Collectors.joining(" -> "));

        String result = String.format("Turn order: %s", order);

        engine.notifyMenuObservers(o -> o.onDisplayMessage(result));
    }

    public static void printGameInfo(BattleData data, BattleEngine engine) {
        List<Entity> currentEnemies = engine.getEntityManager().getAliveEntitiesByType(EntityType.ENEMY);

        Map<String, Long> currentCounts = currentEnemies.stream()
                .collect(Collectors.groupingBy(Entity::getName, Collectors.counting()));

        String currentWaveStr = currentCounts.entrySet().stream()
                .map(e -> e.getValue() + " " + e.getKey())
                .collect(Collectors.joining(" + "));

        if (currentWaveStr.isEmpty()) {
            currentWaveStr = "None";
        }

        Map<String, Long> backupEntityCounts = data.getWaves().stream()
                .skip(data.getWaveCount())
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(name -> name, Collectors.counting()));

        String backupsString = backupEntityCounts.entrySet().stream()
                .map(e -> e.getValue() + " " + e.getKey())
                .collect(Collectors.joining(" + "));

        if (backupsString.isEmpty()) {
            backupsString = "None";
        }

        String result = String.format("Level: %s - %s | Backup : %s", data.difficulty.name, currentWaveStr, backupsString);

        engine.notifyMenuObservers(o -> o.onDisplayMessage(result));
    }
}
