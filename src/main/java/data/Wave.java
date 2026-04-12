package data;

import java.util.List;

public class Wave {
    public final String name;
    public final List<List<Integer>> waves;

    /**
     * Data template class for Wave data.
     * May be used as is rather than a data template due to simplicity.
     */
    public Wave(String name, List<List<Integer>> waves) {
        this.name = name;
        this.waves = waves;
    }
}
