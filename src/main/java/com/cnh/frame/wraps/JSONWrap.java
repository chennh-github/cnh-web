package com.cnh.frame.wraps;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/12/15
 */
public class JSONWrap {

    /**
     * 转JSONObject对象
     *
     * @param jsonObjectString
     * @param createIfNull
     * @return
     */
    public static JSONObject toJSONObject(String jsonObjectString, boolean createIfNull) {
        return StringUtils.isNotBlank(jsonObjectString) ? JSONObject.fromObject(jsonObjectString) : createIfNull ? new JSONObject() : null;
    }

    /**
     * 转JSONArray对象
     *
     * @param jsonArrayString
     * @param createIfNull
     * @return
     */
    public static JSONArray toJSONArray(String jsonArrayString, boolean createIfNull) {
        return StringUtils.isNotBlank(jsonArrayString) ? JSONArray.fromObject(jsonArrayString) : createIfNull ? new JSONArray() : null;
    }

    /**
     * 移除key
     *
     * @param jsonObject
     * @param key        "."分割
     */
    public static void remove(JSONObject jsonObject, String key) {
        if (jsonObject != null && StringUtils.isNotBlank(key)) {
            String[] keys = key.split("\\.");
            Object value = null;
            JSONObject jsonParent = jsonObject;
            for (int i = 0, l = keys.length; i < l; i++) {
                if ((value = jsonParent.get(keys[i])) == null) {
                    break;
                }
                if (i < l - 1) {
                    jsonParent = jsonParent.getJSONObject(keys[i]);
                }
            }
            if (value != null) {
                jsonParent.remove(keys[keys.length - 1]);
            }
        }
    }

    /**
     * 创建内部JSONObject对象
     *
     * @param jsonObject
     * @param key
     * @param ignoreLastKey
     * @return
     */
    public static JSONObject create(JSONObject jsonObject, String key, boolean ignoreLastKey) {
        if (jsonObject != null && StringUtils.isNotBlank(key)) {
            String[] keys = key.split("\\.");
            if (ignoreLastKey) {
                keys = ignoreLast(keys);
            }
            for (String k : keys) {
                if (jsonObject.getJSONObject(k) == null) {
                    jsonObject.put(k, new JSONObject());
                }
                jsonObject = jsonObject.getJSONObject(k);
            }
        }
        return jsonObject;
    }

    /**
     * 根据key取得JSONObject对象
     *
     * @param jsonObject
     * @param key           "."分割
     * @param ignoreLastKey
     * @return
     */
    public static JSONObject get(JSONObject jsonObject, String key, boolean ignoreLastKey) {
        if (jsonObject != null && StringUtils.isNotBlank(key)) {
            String[] keys = key.split("\\.");
            if (ignoreLastKey) {
                keys = ignoreLast(keys);
            }
            for (String k : keys) {
                jsonObject = jsonObject.getJSONObject(k);
            }
        }
        return jsonObject;
    }


    /**
     * JSONObject设值
     *
     * @param jsonObject
     * @param key        "."分割
     * @param value
     */
    public static void set(JSONObject jsonObject, String key, Object value) {
        if (jsonObject != null && StringUtils.isNotBlank(key)) {
            String[] keys = key.split("\\.");
            JSONObject jsonChild = create(jsonObject, key, true);
            jsonChild.put(keys[keys.length - 1], value);
        }
    }

    private static String[] ignoreLast(String[] array) {
        String[] ret = new String[array.length - 1];
        for (int i = 0, l = array.length; i < l; i++) {
            if (i < l - 1) {
                ret[i] = array[i];
            }
        }
        return ret;
    }


    public static void main(String[] args) {
        String s = "{'s': {'a': 1}, 'b': 'b'}";
        JSONObject jsonObject = toJSONObject(s, true);
        remove(jsonObject, "s.a");
        System.out.println(jsonObject.toString());
        set(jsonObject, "s.b", "2");
        System.out.println(jsonObject.toString());
    }

}
