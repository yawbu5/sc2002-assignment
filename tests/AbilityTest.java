package tests;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import src.Ability;
import src.data.JSONLoader;

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
