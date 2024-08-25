package com.mojang.minecraftpe

import android.app.NativeActivity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.media.AudioManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import org.fmod.FMOD
import java.io.File
import java.lang.reflect.Field
import java.nio.ByteBuffer


class MainActivity : NativeActivity() {
    val Launcher: String = "SculptLauncher"
    val UI_HOVER_BUTTON_TEST: Int = 0

    lateinit var packageInfo: PackageInfo
    lateinit var appInfo: ApplicationInfo
    lateinit var libraryDir: String
    lateinit var libraryLocation: String
    var canAccessAssets: Boolean = false
    var apkContext: Context? = null
    lateinit var metrics: DisplayMetrics

    var mcpePackage: Boolean = false
    var minecraftLibBuffer: ByteBuffer? = null
    lateinit var activity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        activity = this
        try {
            packageInfo = packageManager.getPackageInfo("com.mojang.minecraftpe", 0)
            appInfo = packageInfo.applicationInfo
            libraryDir = appInfo.nativeLibraryDir
            libraryLocation = "$libraryDir/libminecraftpe.so"
            Log.i(Launcher, "libminecraftpe.so is located at $libraryDir")
            canAccessAssets = !appInfo.sourceDir.equals(appInfo.publicSourceDir)
            apkContext = if (packageName.equals("com.mojang.minecraftpe")) {
                this
            } else {
                createPackageContext("com.mojang.minecraftpe",
                    Context.CONTEXT_IGNORE_SECURITY);
            }
            Log.i(Launcher, "Start to load library fmod !")
            System.loadLibrary("$libraryDir/libfmod.so")
            Log.i(Launcher, "Start to load minecraftpe !")
            System.loadLibrary(libraryLocation)
            FMOD.init(this)
            if (!FMOD.checkInit()){
                Log.e(Launcher, "Failed to init fmod")
            }
            metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            volumeControlStream = AudioManager.STREAM_MUSIC
        } catch (e: Exception){
            e.message?.let { Log.e(Launcher, it) }
        }
        super.onCreate(savedInstanceState)
    }

    private fun getDeclaredFieldRecursive(clazz: Class<*>?, name: String?): Field? {
        if (clazz == null) return null
        return try {
            name?.let { clazz.getDeclaredField(it) }
        } catch (nsfe: NoSuchFieldException) {
            getDeclaredFieldRecursive(clazz.superclass, name)
        }
    }

    private fun addToFileList(files: Array<File?>, toAdd: File): Array<File?> {
        for (f in files) {
            if (f == toAdd) {
                // System.out.println("Already added path to list");
                return files
            }
        }
        val retval = arrayOfNulls<File>(files.size + 1)
        System.arraycopy(files, 0, retval, 1, files.size)
        retval[0] = toAdd
        return retval
    }

    private fun addLibraryDirToPath(path: String) {
        try {
            val classLoader = classLoader
            val clazz: Class<out ClassLoader> = classLoader.javaClass
            val field: Field? = getDeclaredFieldRecursive(clazz, "pathList")
            field?.isAccessible = true
            val pathListObj: Any? = field?.get(classLoader)
            val pathListClass: Class<out Any>? = pathListObj?.javaClass
            val natfield: Field? = getDeclaredFieldRecursive(
                pathListClass,
                "nativeLibraryDirectories"
            )
            natfield?.isAccessible = true
            val fileList: Array<File?> = natfield?.get(pathListObj) as Array<File?>
            val newList: Array<File?> = addToFileList(fileList, File(path))
            if (!fileList.contentEquals(newList)) natfield.set(pathListObj, newList)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}