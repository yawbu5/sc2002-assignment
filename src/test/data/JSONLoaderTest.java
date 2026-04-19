package src.test.data;

import data.JSONLoader;
import org.junit.jupiter.api.Test;
import src.test.DummyClass;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JSONLoaderTest {

    @Test
    public void EnsureLoadListFromJSONIsSuccessful() {
        List<DummyClass> dummies = JSONLoader.loadList("dummy_data.json", DummyClass.class);

        assertNotNull(dummies);
        assertEquals("John", dummies.get(0).name);
        assertEquals(2, dummies.get(1).id);
        assertEquals("Charlie", dummies.get(2).name);
        assertEquals(4, dummies.get(3).id);
    }

    @Test
    public void EnsureLoadListReturnsNullWhenInvalidInputIsGiven() {
        List<DummyClass> dummies = JSONLoader.loadList("blah", DummyClass.class);

        assertNull(dummies);
    }
}
