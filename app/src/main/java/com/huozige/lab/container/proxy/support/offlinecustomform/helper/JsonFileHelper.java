package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import android.content.Context;
import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class JsonFileHelper {
    static final String FILE_NAME_OFFLINE_LIST = "HAC_OfflinePlusList.json";

    static void writeJsonToExternalStorage(Context context, String fileName, JSONObject jsonObject) {
        if (!isExternalStorageWritable()) {
            return;
        }

        writeJsonToFile(new File(context.getExternalFilesDir(null), fileName), jsonObject);
    }

    static void writeJsonToFile(File file, JSONObject jsonObject) {
        FileOutputStream fos = null;
        try {
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
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

    static boolean deleteFileOrDirectory(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteFileOrDirectory(child);
                }
            }
        }
        return file.delete();
    }

    static JSONObject readJsonFromExternalStorage(Context context, String fileName) {
        if (!isExternalStorageReadable()) {
            return null;
        }

        return readJsonFromFile(new File(context.getExternalFilesDir(null), fileName));
    }

    static JSONObject readJsonFromFile(File file) {
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
