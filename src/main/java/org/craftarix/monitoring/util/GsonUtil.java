package org.craftarix.monitoring.util;

import com.google.gson.Gson;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class GsonUtil {

    private static final Gson gson = new Gson();

    public static String parseJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T unparseJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static Gson getGson() {
        return gson;
    }
}

