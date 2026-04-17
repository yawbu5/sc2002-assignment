package systems;

import data.EntityTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The general rule of thumb for entities in combat is that we don't clear
 * dead entities on the spot until the round/game is done.
 * This allows us to leave room for additional 'revival' functionality
 * and not have to think about weird side effects
 */
public class EntityManager {
    private final Map<Integer, Entity> entityMap = new HashMap<>();
    private int idCount = 0;

    public int count() {
        return entityMap.size();
    }

    public void addEntity(EntityTemplate e) {
        Entity newEntity = Entity.CreateEntity(idCount, e);
        entityMap.put(idCount, newEntity);
        idCount++;
    }

    public void addEntitiesFromList(List<EntityTemplate> list) {
        for (EntityTemplate e : list) {
            this.addEntity(e);
        }
    }

    public Entity getEntity(int id) {
        return entityMap.get(id);
    }

    /**
     * Use to get a List of entity(s) of a certain type.
     * Returns a list of entities of that type.
     * i.e., Retrieve a list of enemies.
     */
    public List<Entity> getEntityByType(EntityType type) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Entity e : entityMap.values()) {
            if (e.getType() == type) {
                result.add(e);
            }
        }

        return result;
    }

    public List<Entity> getAliveEntities() {
        return this.entityMap.values().stream()
                .filter(e -> e.getCurrHp() > 0)
                .collect(Collectors.toList());
    }

    public List<Entity> getAliveEntitiesByType(EntityType type) {
        return this.entityMap.values().stream()
                .filter(e -> e.getCurrHp() > 0)
                .filter(e -> e.getType() == type)
                .collect(Collectors.toList());
    }

    public List<Entity> getAllEntities() {
        return new ArrayList<>(this.entityMap.values());
    }

    public Entity removeEntity(int id) {
        return entityMap.remove(id);
    }

    /**
     * Use for mass removal of entity(s) of the same type.
     * Returns the number of entities left.
     * i.e., Clear all dead enemies before transitioning to next wave.
     */
    public int removeEntityByType(EntityType type) {
        entityMap.entrySet().removeIf(entry -> type.equals(entry.getValue().getType()));

        return this.count();
    }
}
