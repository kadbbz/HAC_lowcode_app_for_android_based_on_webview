package com.huozige.lab.container.utilities;

import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonFileHelper {
    public static final String FILE_NAME_OFFLINE_LIST = "HAC_OfflinePlusList.json";
    public static final String FILE_FLAG_OFFLINE_LIST  = "project";

    public static final String FILE_NAME_OFFLINE_FORM = "HAC_OfflinePlusForm_%s.json";
    public static final String FILE_FLAG_OFFLINE_FORM = "customForm";


    public static void writeJsonToExternalStorage(Context context, String fileName, String patternId, JSONObject jsonObject) {
        writeJsonToExternalStorage(context, String.format(fileName, patternId), jsonObject);
    }

    public static void writeJsonToExternalStorage(Context context, String fileName, JSONObject jsonObject) {
        if (!isExternalStorageWritable()) {
            return;
        }

        FileOutputStream fos = null;
        try {
            File file = new File(context.getExternalFilesDir(null), fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            String jsonString = jsonObject.toString();
            fos.write(jsonString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static JSONObject readJsonToExternalStorage(Context context, String fileName, String patternId) {
        return readJsonFromExternalStorage(context, String.format(fileName, patternId));
    }

    public static JSONObject readJsonFromExternalStorage(Context context, String fileName) {
        if (!isExternalStorageReadable()) {
            return null;
        }

        File file = new File(context.getExternalFilesDir(null), fileName);
        if (!file.exists()) {
            return null;
        }

        FileInputStream fis;
        BufferedReader reader = null;
        try {
            fis = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(fis));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String jsonString = stringBuilder.toString();
            return new JSONObject(jsonString);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static JSONObject readTemplateJson() {
        if (!isExternalStorageReadable()) {
            return null;
        }

        File file = new File("TemplateCustomForm.json");
        if (!file.exists()) {
            return null;
        }

        FileInputStream fis;
        BufferedReader reader = null;
        try {
            fis = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(fis));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String jsonString = stringBuilder.toString();
            return new JSONObject(jsonString);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
