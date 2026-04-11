package src.data;

import java.util.List;

public class Wave {
    public final String name;
    public final List<List<Integer>> waves;


    public Wave(String name, List<List<Integer>> waves) {
        this.name = name;
        this.waves = waves;
    }
}
