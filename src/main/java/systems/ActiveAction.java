package systems;

import java.util.ArrayList;
import java.util.List;

public class ActiveAction {
    private int casterId;    // casting entity ID (i.e., primary key)
    private List<Integer> targetId = new ArrayList<>();
    private Cooldown cooldown;

    public ActiveAction(int casterId, Cooldown cooldown) {
        this.casterId = casterId;
        this.cooldown = cooldown;
    }

    public void tick() {
        cooldown.tick();
    }

    public boolean isReady() {
        return cooldown.isReady();
    }
}
