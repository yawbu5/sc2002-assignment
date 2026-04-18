package systems.states.battle;

import data.Wave;
import systems.BattleEngine;
import systems.Entity;
import systems.EntityType;

import java.util.ArrayList;
import java.util.List;

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
        return (currentTurn == 0);
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

    public static void printWaveInfo(BattleEngine engine) {
        for (Entity e : engine.getEntityManager().getAliveEntities()) {
            String playerStatus = (e.getType() == EntityType.PLAYER) ? " (YOU)" : "";

            String msg = e.getName() + playerStatus + " | HP: " + e.getCurrHp() + "/" + e.getMaxHp() + ", DEF: " + e.getDefence() + ", SPD: " + e.getSpeed();
            engine.notifyMenuObservers(o -> o.onDisplayMessage(msg));
        }
    }

    public void setPlayerInventory(List<String> inv) {
        this.playerInventory = inv;
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
}
