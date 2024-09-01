package com.chen.sculptlauncher.core;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Field;

import dalvik.system.PathClassLoader;

public class MyClassList {
    public static void addLibraryDirToPath(String path, Context context){
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class<? extends ClassLoader> clazz = classLoader.getClass();
            Field field = getDeclaredFieldRecursive(clazz, "pathList");
            field.setAccessible(true);
            Object pathListObj = field.get(classLoader);
            Class<?> pathListClass = null;
            if (pathListObj != null) {
                pathListClass = pathListObj.getClass();
            }
            Field natfield = getDeclaredFieldRecursive(pathListClass,
                    "nativeLibraryDirectories");
            if (natfield != null) {
                natfield.setAccessible(true);
            }
            File[] fileList = null;
            if (natfield != null) {
                System.out.println(natfield);
                fileList = (File[]) natfield.get(pathListObj);
            }
            File[] newList = addToFileList(fileList, new File(path));
            if (fileList != newList)
                natfield.set(pathListObj, newList);
            // check
            System.out.println("Class loader shenanigans: " +
            ((PathClassLoader) context.getClassLoader()).findLibrary("minecraftpe"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File[] addToFileList(File[] files, File toAdd) {
        for (File f : files) {
            if (f.equals(toAdd)) {
                System.out.println("Already added path to list");
                return files;
            }
        }
        File[] retval = new File[files.length + 1];
        System.arraycopy(files, 0, retval, 1, files.length);
        retval[0] = toAdd;
        return retval;
    }

    public static Field getDeclaredFieldRecursive(Class<?> clazz, String name) {
        if (clazz == null)
            return null;
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException nsfe) {
            return getDeclaredFieldRecursive(clazz.getSuperclass(), name);
        }
    }

}
