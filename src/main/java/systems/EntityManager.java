package systems;

import java.util.HashMap;
import java.util.Map;

public class EntityManager {
    private final Map<Integer, Entity> entityMap = new HashMap<>();
    private int idCount = 0;

    public EntityManager() {

    }

    public int count() {
        return entityMap.size();
    }

    public void AddEntity(Entity e) {
        Entity newEntity = Entity.CreateEntity(idCount, e);
        entityMap.put(idCount, newEntity);
        idCount++;
    }

    public Entity GetEntity(int id) {
        return entityMap.get(id);
    }

    public Entity RemoveEntity(int id) {
        return entityMap.remove(id);
    }

}
