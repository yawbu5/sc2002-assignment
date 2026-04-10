package tests.data;

import org.junit.Assert;
import org.junit.Test;
import src.data.JSONLoader;
import tests.DummyClass;

import java.util.List;

public class JSONLoaderTest {

    @Test
    public void testLoadListFromJSON() {
        List<DummyClass> dummies = JSONLoader.loadList("resources/dummy_data.json", DummyClass.class);

        Assert.assertNotNull(dummies);
        Assert.assertEquals("John", dummies.get(0).name);
        Assert.assertEquals(2, dummies.get(1).id);
        Assert.assertEquals("Charlie", dummies.get(2).name);
        Assert.assertEquals(4, dummies.get(3).id);
    }

    @Test
    public void testLoadListReturnsNothingWhenInvalidInputIsGiven() {
        List<DummyClass> dummies = JSONLoader.loadList("blah", DummyClass.class);

        Assert.assertNull(dummies);
    }
}
