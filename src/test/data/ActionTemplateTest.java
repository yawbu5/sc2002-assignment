package src.test.data;

import data.ActionTemplate;
import data.JSONLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTemplateTest {

    @Test
    public void EnsureCreateAbilityListFromJSONIsSuccessful() {
        List<ActionTemplate> actionTemplates = JSONLoader.loadList("actions.json", ActionTemplate.class);

        assertNotNull(actionTemplates);
        assertEquals(8, actionTemplates.size());
        assertEquals("basicattack", actionTemplates.get(0).id);
        assertEquals("defend", actionTemplates.get(1).id);
        assertEquals("stun", actionTemplates.get(2).id);
    }
}
