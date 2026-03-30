package tests;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import src.Ability;
import src.AbilityParams;

public class AbilityTest {

    @Test
    public void CreateAbilityListFromJSON() {
        List<Ability> abilities = AbilityParams.LoadAbilities("abilities.json");

        Assert.assertEquals("BasicAttack", abilities.get(0).name);
        Assert.assertEquals("Defend", abilities.get(1).name);
        Assert.assertEquals("Stun", abilities.get(2).name);
    }
}
