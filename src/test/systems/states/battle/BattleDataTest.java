package src.test.systems.states.battle;

import data.Wave;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import systems.states.battle.BattleData;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BattleDataTest {

    private BattleData data;

    @BeforeEach
    public void setup() {
        data = new BattleData();

        List<List<String>> testWaves = Arrays.asList(
                Collections.singletonList("Goblin"),
                Arrays.asList("Wolf", "Goblin")
        );

        Wave testDifficulty = new Wave("Test", testWaves);
        data.setDifficulty(testDifficulty);
    }

    @Test
    public void testRestClearsDataCorrectly() {
        data.incrementRoundCounter();
        data.incrementWaveCount();
        data.currentTurn = 69;

        data.reset();

        assertEquals(1, data.getRoundCounter());
        assertEquals(1, data.getRoundCounter());
        assertEquals(0, data.currentTurn);
    }

    @Test
    public void testIncrementingWaveCountReturnsCorrectIndexAndClamps() {
        assertEquals(1, data.getWaveCount());

        int nextInd = data.incrementWaveCount();
        assertEquals(2, data.getWaveCount());
        assertEquals(1, nextInd);

        data.incrementWaveCount();
        assertEquals(-1, data.incrementWaveCount());
    }

    @Test
    public void testCurrentTurnIsPlayerIsCorrect() {
        data.setTurnOrder(Arrays.asList(0,1));

        data.currentTurn = 1;
        assertTrue(data.currentTurnIsPlayer());

        data.currentTurn = 2;
        assertFalse(data.currentTurnIsPlayer());
    }
}
