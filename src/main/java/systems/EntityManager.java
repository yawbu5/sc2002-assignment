package systems;

import data.EntityTemplate;

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

    public String AddEntity(EntityTemplate e ) {
        Entity newEntity = Entity.CreateEntity(idCount, e);
        entityMap.put(idCount, newEntity);
        idCount++;

        return (idCount + ": " + newEntity.getName());
    }

    public void AddEntitiesFromList(List<EntityTemplate> list) {
       for (EntityTemplate e : list) {
           this.AddEntity(e);
       }
    }

    public Entity GetEntity(int id) {
        return entityMap.get(id);
    }

    public Entity RemoveEntity(int id) {
        return entityMap.remove(id);
    }

}
