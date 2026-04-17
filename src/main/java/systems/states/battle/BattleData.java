package systems.states.battle;

import data.Wave;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds (temporary) battle-specific context/data.
 * Makes it easier to restart the game
 */
public class BattleData {
    private Wave difficulty;
    private int waveCount = 0;
    private int roundCounter = 1;
    private List<Integer> turnOrder;
    public int currentTurn = 0;     // on initial startTurn check, this number is current. However on post-checks we consider this to be currentTurn++
    public boolean playerWins = false;

    public void setDifficulty(Wave difficulty) {
        this.difficulty = difficulty;
    }

    public void incrementRoundCounter() {
        this.roundCounter++;
    }

    public boolean currentTurnIsPlayer() {
        return (currentTurn == 0);
    }

    /**
     * Wave counts are one-based.
     */
    public int incrementWaveCount() {
        if (this.waveCount - 1 >= difficulty.waves.size()) {
            return -1;
        } else {
            this.waveCount++;
            return this.waveCount;
        }
    }

    public void setTurnOrder(List<Integer> list) {
       this.turnOrder = list;
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
}
