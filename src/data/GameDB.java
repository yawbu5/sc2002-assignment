package src.data;

import src.Ability;
import src.Entity;

import java.util.List;

/**
 * Holds the loaded data
 */
public class GameDB {
    public final List<Ability> abilities;
    public final List<Entity> entities;
    // TODO: public final List<Wave> waves;

    public GameDB() {
        abilities = JSONLoader.loadList("resources/abilities.json", Ability.class);
        entities = JSONLoader.loadList("resources/entities.json", Entity.class);
    }
}
