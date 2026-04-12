package systems;

import data.EntityTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityManager {
    private final Map<Integer, Entity> entityMap = new HashMap<>();
    private int idCount = 0;

    public EntityManager() {

    }

    public int count() {
        return entityMap.size();
    }

    public void AddEntity(EntityTemplate e) {
        Entity newEntity = Entity.CreateEntity(idCount, e);
        entityMap.put(idCount, newEntity);
        idCount++;
    }

    public void AddEntitiesFromList(List<EntityTemplate> list) {
       for (EntityTemplate e : list) {
           this.AddEntity(e);
       }
    }

    public Entity GetEntity(int id) {
        return entityMap.get(id);
    }

    /**
     * Use to get a List of entity(s) of a certain type.
     * Returns a list of entites of that type.
     * i.e., Retrieve a list of enemies.
     * @param type EntityType
     * @return List
     */
    public List<Entity> GetEntityByType(Entity.EntityType type) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Entity e : entityMap.values()) {
            if (e.getType() == type) {
                result.add(e);
            }
        }

        return result;
    }

    public Entity RemoveEntity(int id) {
        return entityMap.remove(id);
    }
    /**
     * Use for mass removal of entity(s) of the same type.
     * Returns the number of entities left.
     * i.e., Remove all enemies.
     * @param type EntityType
     * @return int
    */
    public int RemoveEntityByType(Entity.EntityType type) {
        entityMap.entrySet().removeIf(entry -> type.equals(entry.getValue().getType()));

        return this.count();
    }

}
