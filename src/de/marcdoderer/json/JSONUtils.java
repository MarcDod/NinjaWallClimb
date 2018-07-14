package de.marcdoderer.json;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;

public class JSONUtils {
    public static String getJSONStringFromFile(File file) throws IOException {
        Scanner scanner;
        scanner = new Scanner(file);
        String json = scanner.useDelimiter("\\z").next();
        scanner.close();
        return json;
    }

    public static JSONObject getJSONObjectFromFile(File file) throws IOException {
        return new JSONObject(getJSONStringFromFile(file));
    }

    public static boolean objectExists(JSONObject jsonObjects, String key){
        Object o;
        try{
            o = jsonObjects.get(key);
        } catch (Exception e){
            return false;
        }
        return o != null;
    }
}
