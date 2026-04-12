package data;

import java.util.List;

/**
 * Holds the loaded data
 */
public class GameResources {
    public final List<Ability> abilities;
    public final List<EntityTemplate> entityTemplates;
    public final List<Wave> waves;

    public GameResources() {
        abilities = JSONLoader.loadList("abilities.json", Ability.class);
        entityTemplates = JSONLoader.loadList("entities.json", EntityTemplate.class);
        waves = JSONLoader.loadList("waves.json", Wave.class);
    }
}
