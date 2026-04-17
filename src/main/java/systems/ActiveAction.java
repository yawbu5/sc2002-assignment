package systems;

import java.util.ArrayList;
import java.util.List;

public class ActiveAction {
    private int casterId;    // casting entity ID (i.e., primary key)
    private List<Integer> targetId = new ArrayList<>();
    private int cooldownTimer;

    public ActiveAction(int casterId) {
        this.casterId = casterId;
    }

    public void setCooldown(int cooldown) {
        this.cooldownTimer = cooldown;
    }

    public void tick() {
        if (this.cooldownTimer > 0)
            this.cooldownTimer--;
    }

    public boolean isReady() {
        return cooldownTimer == 0;
    }
}
