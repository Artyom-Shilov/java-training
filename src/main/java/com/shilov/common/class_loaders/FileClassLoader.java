package com.shilov.common.class_loaders;

import java.io.*;

public class FileClassLoader extends ClassLoader {

    private static final FileClassLoader INSTANCE = new FileClassLoader();

    private FileClassLoader() {}

    public static FileClassLoader getInstance() {
        return INSTANCE;
    }

    public Class<?> loadClassFromFile(String filePath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            byte[] bytes = fileInputStream.readAllBytes();
            return defineClass(null, bytes, 0, bytes.length);
        }
    }
}
