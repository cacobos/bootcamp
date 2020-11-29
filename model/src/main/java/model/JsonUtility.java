package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtility {

    public static <T> List<T> parseJSONList(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(jsonString, type);
    }
}
