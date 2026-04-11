package src.data;

import src.Entity;

import java.util.List;

/**
 * Holds the loaded data
 */
public class GameResources {
    public final List<Ability> abilities;
    public final List<Entity> entities;
    public final List<Wave> waves;

    public GameResources() {
        abilities = JSONLoader.loadList("resources/abilities.json", Ability.class);
        entities = JSONLoader.loadList("resources/entities.json", Entity.class);
        waves = JSONLoader.loadList("resources/waves.json", Wave.class);
    }
}
