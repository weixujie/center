package net.tiangu.center.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {
    public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static String exec() {
        return exec(true);
    }

    public static String exec(boolean success) {
        return exec(success, null, null, null, null);
    }

    public static String exec(Exception e) {
        e.printStackTrace();
        if (e.getCause() == null) {
            return exec(false, e.getMessage(), null, null, null);
        }
        return exec(false, e.getCause().getMessage(), null, null, null);
    }

    public static String exec(String message) {
        return exec(false, message, null, null, null);
    }

    public static String exec(String name, Object value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("value", value);
        return exec(true, null, map, null, null);
    }

    public static String exec(Map<String, ?> result) {
        return exec(true, null, null, result, null);
    }

    public static String exec(List<?> list) {
        return exec(true, null, null, null, list);
    }

    public static String exec(boolean success, String message, Map<String, Object> map, Map<String, ?> result, List<?> list) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("success", success);
        res.put("message", message);
        if (map != null) {
            res.put(String.valueOf(map.get("name")), map.get("value"));
        }
        res.put("result", result);
        if (list != null) {
            res.put("list", list);
        }
        return gson.toJson(res);
    }
}
