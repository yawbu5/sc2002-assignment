package src.test.data;

import data.JSONLoader;
import org.junit.jupiter.api.Test;
import systems.entities.Entity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTemplateTest {

    @Test
    public void EnsureCreateEntityListFromJSONIsSuccessful() {
        List<Entity> entities = JSONLoader.loadList("entities.json", Entity.class);

        assertNotNull(entities);
        assertEquals(4, entities.size());
        assertEquals("Warrior", entities.get(0).getName());
        assertEquals("Wizard", entities.get(1).getName());
        assertEquals("Goblin", entities.get(2).getName());
        assertEquals("Wolf", entities.get(3).getName());
    }
}
