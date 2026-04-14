package data;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Holds the loaded data
 */
public class GameResources {
    public final List<ActionTemplate> abilities;
    public final List<EntityTemplate> entityTemplates;
    public final List<Wave> waves;

    public GameResources() {
        abilities = JSONLoader.loadList("actions.json", ActionTemplate.class);
        entityTemplates = JSONLoader.loadList("entities.json", EntityTemplate.class);
        waves = JSONLoader.loadList("waves.json", Wave.class);
    }

    public static <T> List<T> getListByPredicate(List<T> inputList, Predicate<T> pred) {
        return inputList.stream()
                .filter(pred)
                .collect(Collectors.toList());
    }
}
