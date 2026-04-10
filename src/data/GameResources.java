package src.data;

import src.Ability;
import src.Entity;
import src.Wave;

import java.util.List;

/**
 * Holds the loaded data
 */
public class GameResources {
    public List<Ability> abilities;
    public List<Entity> entities;
    public List<Wave> waves;

    public GameResources() {
        abilities = JSONLoader.loadList("resources/abilities.json", Ability.class);
        entities = JSONLoader.loadList("resources/entities.json", Entity.class);
        waves = JSONLoader.loadList("resources/waves.json", Wave.class);
    }
}
