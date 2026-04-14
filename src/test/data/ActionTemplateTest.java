package src.test.data;

import data.ActionTemplate;
import data.JSONLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ActionTemplateTest {

    @Test
    public void EnsureCreateAbilityListFromJSONIsSuccessful() {
        List<ActionTemplate> abilities = JSONLoader.loadList("actions.json", ActionTemplate.class);

        Assert.assertNotNull(abilities);
        Assert.assertEquals(8, abilities.size());
        Assert.assertEquals("basicattack", abilities.get(0).id);
        Assert.assertEquals("defend", abilities.get(1).id);
        Assert.assertEquals("stun", abilities.get(2).id);
    }
}
