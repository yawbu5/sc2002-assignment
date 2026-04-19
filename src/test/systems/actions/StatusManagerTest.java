package src.test.systems.actions;

import data.ActionEffectTemplate;
import data.EffectTemplate;
import data.EntityTemplate;
import data.GameResources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import systems.BattleEngine;
import systems.actions.StatusManager;
import systems.entities.Entity;
import systems.entities.EntityType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StatusManagerTest {
    private BattleEngine testEngine;
    private StatusManager statusManager;
    private Entity testEntity;
    private GameResources db;

    @BeforeEach
    public void setup() {
        db = new GameResources();
        testEngine = new BattleEngine(db);
        testEngine.startStatusManager();
        testEngine.startActionManager();

        statusManager = testEngine.getStatusManager();
        EntityTemplate template = new EntityTemplate(
                "Player", 100, 50, 20, 10, new ArrayList<>(), "", EntityType.PLAYER
        );

        List<EntityTemplate> entities = new ArrayList<>();
        entities.add(template);
        testEngine.startEntityManager(entities);
        testEngine.getEntityManager().addEntity(template);
        testEntity = testEngine.getEntityManager().getEntity(0);
    }

    @Test
    public void testProcessEffectIncrementsStacksAndRefreshesDuration() {
        statusManager.processEffect(0, new ActionEffectTemplate("APPLY_STATUS", "increase_damage", 2, 0));
        assertEquals(1, statusManager.getActiveEffects(0).get("increase_damage").stacks);
        assertEquals(2, statusManager.getActiveEffects(0).get("increase_damage").duration);


        statusManager.processEffect(0, new ActionEffectTemplate("APPLY_STATUS", "increase_damage", 4, 0));
        assertEquals(2, statusManager.getActiveEffects(0).get("increase_damage").stacks);
        assertEquals(4, statusManager.getActiveEffects(0).get("increase_damage").duration);
    }

    @Test
    public void testTickEffectsDecrementsDurationAndRemovesOnZero() {
        statusManager.processEffect(0, new ActionEffectTemplate("APPLY_STATUS", "dmg_buff", 2, 0));

        statusManager.tickEffects();
        assertTrue(statusManager.getActiveEffects(0).containsKey("dmg_buff"));
        assertEquals(1, statusManager.getActiveEffects(0).get("atk_buff").duration);

        statusManager.tickEffects();
        assertFalse(statusManager.getActiveEffects(0).containsKey("atk_buff"));
    }
}