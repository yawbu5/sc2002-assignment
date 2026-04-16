package systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Keeps track of available abilities for entities on the field and their cooldowns.
 */
public class ActionManager {
    // We keep track by entityId and a list of ActiveActions (holds cooldowns)
    private final Map<Integer, List<ActiveAction>> activeActions = new HashMap<>();

    public ActionManager() {

    }

    public void registerEntityActions(int entityId, List<ActiveAction> actions) {
        activeActions.put(entityId, actions);
    }

    public void deregisterEntityActions(int entityId) {
        activeActions.remove(entityId);
    }

    public List<ActiveAction> getAllActions(int entityId) {
        return activeActions.getOrDefault(entityId, new ArrayList<>());
    }

    public List<ActiveAction> getAvailableActions(int entityId) {
        List<ActiveAction> actions = getAllActions(entityId);

        return actions.stream().filter(ActiveAction::isReady).collect(Collectors.toList());
    }

    public void incrementTick(int entityId) {
        List<ActiveAction> actions = getAllActions(entityId);
        for (ActiveAction a : actions) {
            a.tick();
        }
    }
}
