package src;
import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class AbilityParams {


    public static AbilityConfig[] LoadEntities() {
        Reader reader = new InputStreamReader(Objects.requireNonNull(AbilityConfig.class.getResourceAsStream("/abilities.json")));
        Gson gson = new Gson();

        AbilityConfig[] abilities = new AbilityConfig[]{gson.fromJson(reader, AbilityConfig.class)};

        for (AbilityConfig a : abilities) {
            System.out.println("Loaded: " + a.name);
        }

        return abilities;
    }
}

