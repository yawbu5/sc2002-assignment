package src.test.systems.entities;

import data.EntityTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import systems.entities.Entity;
import systems.entities.EntityManager;
import systems.entities.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntityManagerTest {
    private EntityManager manager;
    private EntityTemplate enemyTemplate;
    private EntityTemplate playerTemplate;

    @BeforeEach
    public void setup() {
        manager = new EntityManager();

        enemyTemplate = new EntityTemplate("Orc", 50, 10, 5, 20, new ArrayList<>(), null, EntityType.ENEMY);
        playerTemplate = new EntityTemplate("Player", 100, 20, 10, 30, new ArrayList<>(), "special", EntityType.PLAYER);
    }

    @Test
    public void testAddingEntityIncreasesCountAndAssignsCorrectId() {
        assertEquals(0, manager.count());

        manager.addEntity(playerTemplate);
        assertEquals(1, manager.count());

        Entity retrievedEntity = manager.getEntity(0);
        assertNotNull(retrievedEntity);
        assertTrue(retrievedEntity.isPlayer());
    }

    @Test
    public void testAddingEntitiesFromListAddsAllEntities() {
        List<EntityTemplate> testWave = Arrays.asList(playerTemplate, enemyTemplate, enemyTemplate);
        manager.addEntitiesFromList(testWave);

        assertEquals(3, manager.count());
        assertEquals(2, manager.getEntityByType(EntityType.ENEMY).size());
        assertEquals(1, manager.getEntityByType(EntityType.PLAYER).size());
    }

    @Test
    public void testGetAliveEntitiesFiltersOutDeadEntities() {
        manager.addEntity(playerTemplate);  // 0
        manager.addEntity(enemyTemplate);  // 1
        manager.addEntity(enemyTemplate);  // 2

        manager.getEntity(1).setCurrHp(0);

        List<Entity> aliveEntities = manager.getAliveEntities();

        assertEquals(2, aliveEntities.size());  // should only return 2 alive entities

        boolean hasDeadEnemy = false;
        for (Entity e : aliveEntities) {
            if (e.getId() == 1) {
                hasDeadEnemy = true;
                break;
            }
        }
        assertFalse(hasDeadEnemy);
    }

    @Test
    public void testRemovingEntityByTypeClearsOnlyThatType() {
        manager.addEntity(playerTemplate);
        manager.addEntity(enemyTemplate);
        manager.addEntity(enemyTemplate);

        int remainingEntities = manager.removeEntityByType(EntityType.ENEMY);

        assertEquals(1, remainingEntities);
        assertEquals(1, manager.count());
        assertTrue(manager.getEntity(0).isPlayer());
    }
}