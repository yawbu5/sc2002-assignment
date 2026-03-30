package src;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EntityManager {
    final ArrayList<Entity> entities = new ArrayList<>();
    final ArrayList<Entity> toAdd = new ArrayList<>();
    final Map<String, Integer> entityMap = new HashMap<>();
    private int count = 0;

    public EntityManager() {

    }

    private int _incr_count() {
       return count++;
    }

    public void DeleteEntity(int id) {
        return;
    }

    public void AddEntity(Entity e) {
        return;
    }

    public void UpdateEntities() {

    }
}
