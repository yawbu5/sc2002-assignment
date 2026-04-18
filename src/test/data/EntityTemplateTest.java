package src.test.data;

import data.JSONLoader;
import org.junit.Assert;
import org.junit.Test;
import systems.entities.Entity;

import java.util.List;

public class EntityTemplateTest {

    @Test
    public void EnsureCreateEntityListFromJSONIsSuccessful() {
        List<Entity> entities = JSONLoader.loadList("entities.json", Entity.class);

        Assert.assertNotNull(entities);
        Assert.assertEquals(4, entities.size());
        Assert.assertEquals("Warrior", entities.get(0).getName());
        Assert.assertEquals("Wizard", entities.get(1).getName());
        Assert.assertEquals("Goblin", entities.get(2).getName());
        Assert.assertEquals("Wolf", entities.get(3).getName());
    }
}
