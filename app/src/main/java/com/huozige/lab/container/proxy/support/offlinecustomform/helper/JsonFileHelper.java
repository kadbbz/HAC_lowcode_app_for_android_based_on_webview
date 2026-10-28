package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class JsonFileHelper {
    static void writeObjectToFile(File file, Object value) {
        writeStringToFile(file, JSON.toJSONString(value));
    }

    static <T> T readObjectFromFile(File file, Class<T> clazz) {
        String jsonString = readStringFromFile(file);
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            return JSON.parseObject(jsonString, clazz);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    static JSONObject readJsonFromFile(File file) {
        String jsonString = readStringFromFile(file);
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            return JSON.parseObject(jsonString);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeStringToFile(File file, String value) {
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
            fos.write(value.getBytes());
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
                    if (!deleteFileOrDirectory(child)) {
                        return false;
                    }
                }
            }
        }
        return file.delete();
    }

    private static String readStringFromFile(File file) {
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

            return stringBuilder.toString();

        } catch (IOException e) {
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

}
