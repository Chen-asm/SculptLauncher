package com.chen.sculptlauncher.core.file

import android.annotation.SuppressLint
import cn.hutool.core.io.FileUtil
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import java.io.File

object DexHelper {
    @SuppressLint("DiscouragedPrivateApi")
    fun patchDexFile(classLoader: ClassLoader, dexPath: String, otherDex: String){
        val dexOptFilePath = "${File(otherDex).parent!!}/patched"
        FileUtil.mkdir(dexOptFilePath)
        val field1 = BaseDexClassLoader::class.java.getDeclaredField("pathList")
        field1.isAccessible = true
        val dexPathList = field1.get(classLoader)
        val field2 = dexPathList.javaClass.getDeclaredField("dexElements")
        field2.isAccessible = true
        val dexElements = field2.get(dexPathList)
        val dcl = DexClassLoader(dexPath, dexOptFilePath, null, classLoader)
        val patchDexPathList = field1.get(dcl)
        val patchDexElements = field2.get(patchDexPathList)
        val concatArray: Any?

        val len1: Int = (patchDexElements as? Array<*>)?.size ?: 0
        val len2: Int = (dexElements as? Array<*>)?.size ?: 0
        val totalLen = len1 + len2
        concatArray = java.lang.reflect.Array.newInstance(patchDexElements.javaClass.componentType!!, totalLen)
        for (i in 0 until len1) {
            java.lang.reflect.Array.set(concatArray, i,
                patchDexElements?.let { java.lang.reflect.Array.get(it, i) })
        }
        for (j in 0 until len2) {
            java.lang.reflect.Array.set(concatArray, len1 + j,
                dexElements?.let { java.lang.reflect.Array.get(it, j) })
        }
        field2.set(dexPathList, concatArray);
    }
}