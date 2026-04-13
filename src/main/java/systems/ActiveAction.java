package systems;

import java.util.ArrayList;
import java.util.List;

public class ActiveAction {
    public List<Integer> targetId = new ArrayList<>();
    public int casterId;
    public Cooldown cooldown;
}
