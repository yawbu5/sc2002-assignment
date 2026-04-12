package data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class JSONLoader {

    /**
     * Loads in a list of data from a JSON file, structured according to the class specified.
     * Note that the path is relative to where the class is implemented.
     *
     * @param filePath The path to the config file, usually "resources/xxx.json".
     * @param cls The class to specify as the blueprint. Its parameters must match the structure in JSON.
     * @return List
     */
    public static <T> List<T> loadList(String filePath, Class<T> cls) {
        Gson gson = new Gson();
        List<T> items = null;

        try {
            InputStream is = cls.getResourceAsStream(filePath);

            if (is == null) {
                System.out.println("FAILED TO LOAD CONFIG: " + filePath);
                return null;
            }

            try (Reader reader = new InputStreamReader(is)) {
                Type listType = TypeToken.getParameterized(List.class, cls).getType();

                items = gson.fromJson(reader, listType);
            }
        } catch (Exception e) {
            System.out.println("ERROR LOADING CONFIG: " + filePath +": " + e.getMessage());
        }

        return items;
    }

    //public static <T> Map<Integer, T> convertListToMap(List<T> list) {
    //    Map<Integer, T> map = new HashMap<>();
    //    int count = 0;
    //    for (T item: list) {
    //        map.put(count++, item);
    //    }
    //    return map;
    //}
}
