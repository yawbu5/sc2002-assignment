package src;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.lang.reflect.Type;

public class AbilityParams {


    public static List<Ability> LoadAbilities(String filePath) {
        Gson gson = new Gson();
        List<Ability> abilities = null;

        try (Reader reader = new InputStreamReader(AbilityParams.class.getResourceAsStream(filePath))) {
            Type listType = new TypeToken<List<Ability>>(){}.getType();
            abilities = gson.fromJson(reader, listType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return abilities;
    }
}

