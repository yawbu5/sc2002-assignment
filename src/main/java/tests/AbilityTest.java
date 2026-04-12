package tests;

import org.junit.Assert;
import org.junit.Test;
import data.Ability;
import data.JSONLoader;

import java.util.List;

public class AbilityTest {

    @Test
    public void testCreateAbilityListFromJSON() {
        List<Ability> abilities = JSONLoader.loadList("resources/abilities.json", Ability.class);

        Assert.assertNotNull(abilities);
        Assert.assertEquals(8, abilities.size());
        Assert.assertEquals("basicattack", abilities.get(0).id);
        Assert.assertEquals("defend", abilities.get(1).id);
        Assert.assertEquals("stun", abilities.get(2).id);
    }
}
