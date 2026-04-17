package src.test.data;

import data.ActionTemplate;
import data.JSONLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ActionTemplateTest {

    @Test
    public void EnsureCreateAbilityListFromJSONIsSuccessful() {
        List<ActionTemplate> actionTemplates = JSONLoader.loadList("actions.json", ActionTemplate.class);

        Assert.assertNotNull(actionTemplates);
        Assert.assertEquals(8, actionTemplates.size());
        Assert.assertEquals("basicattack", actionTemplates.get(0).id);
        Assert.assertEquals("defend", actionTemplates.get(1).id);
        Assert.assertEquals("stun", actionTemplates.get(2).id);
    }
}
