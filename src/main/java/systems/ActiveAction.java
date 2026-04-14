package systems;

import java.util.ArrayList;
import java.util.List;

public class ActiveAction {
    public int casterId;    // casting entity ID (i.e., primary key)
    public List<Integer> targetId = new ArrayList<>();
    public Cooldown cooldown;
}
