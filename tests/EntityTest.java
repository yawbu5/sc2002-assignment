package tests;

import org.junit.Assert;
import org.junit.Test;
import src.Entity;
import src.data.JSONLoader;

import java.util.List;

public class EntityTest {

    @Test
    public void testCreateEntityListFromJSON() {
        List<Entity> entities = JSONLoader.loadList("resources/entityTemplates.json", Entity.class);

        Assert.assertNotNull(entities);
        Assert.assertEquals(4, entities.size());
        Assert.assertEquals("Warrior", entities.get(0).name);
        Assert.assertEquals("Wizard", entities.get(1).name);
        Assert.assertEquals("Goblin", entities.get(2).name);
        Assert.assertEquals("Wolf", entities.get(3).name);
    }
}
