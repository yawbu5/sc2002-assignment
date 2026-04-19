package src.test.systems.entities;

import data.EntityTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import systems.entities.Entity;
import systems.entities.EntityType;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
    private Entity entity;

    @BeforeEach
    public void setup() {
        EntityTemplate player = new EntityTemplate(
                "Player", 100, 20, 10, 30, new ArrayList<>(), null, EntityType.PLAYER
        );
        entity = Entity.CreateEntity(1, player);
    }

    @Test
    public void testInitalisatingSetsCurrAndMaxHPCorrectly() {
        assertEquals(100, entity.getMaxHp());
        assertEquals(100, entity.getCurrHp());
        assertFalse(entity.isDead());
    }

    @Test
    public void testSettingCurrHpClampsToMaxValueWhenOverhealed() {
       entity.setCurrHp(50);
       assertEquals(50, entity.getCurrHp());

       entity.setCurrHp(entity.getCurrHp() + 999);

       assertEquals(100, entity.getCurrHp());
    }

    @Test
    public void testSetCurrHpClampsToZeroWhenTakingFatalDamage() {
        entity.setCurrHp(entity.getCurrHp() - 150);

        assertEquals(0, entity.getCurrHp());
        assertTrue(entity.isDead());
    }
}
