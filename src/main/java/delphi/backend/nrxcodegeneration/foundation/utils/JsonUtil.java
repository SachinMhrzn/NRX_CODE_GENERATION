package delphi.backend.nrxcodegeneration.foundation.utils;

import com.google.gson.JsonObject;

public class JsonUtil {
    public JsonUtil() {
    }

    /**
     * Create a JsonObject with a key and value
     *
     * @param key   The key of the JSON object.
     * @param value The value of the key.
     * @return A JsonObject with a key and value.
     */
    public static JsonObject getJsonObject(String key, String value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key, value);
        return jsonObject;
    }
}
