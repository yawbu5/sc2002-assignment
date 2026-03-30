package src;

public class ActionManager {
    private static final int av_val = 10000;

    public ActionManager() {

    }

    private int _calculate_av(int speed) {
        return speed / av_val;
    }
}
