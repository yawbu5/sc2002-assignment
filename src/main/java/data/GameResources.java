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
        abilities = JSONLoader.loadList("resources/abilities.json", Ability.class);
        entityTemplates = JSONLoader.loadList("resources/entityTemplates.json", EntityTemplate.class);
        waves = JSONLoader.loadList("resources/waves.json", Wave.class);
    }
}
