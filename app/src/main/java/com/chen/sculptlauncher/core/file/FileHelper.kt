package com.chen.sculptlauncher.core.file

import android.content.Context
import android.content.Intent
import cn.hutool.core.io.FileUtil
import cn.hutool.core.util.ZipUtil
import com.alibaba.fastjson2.JSONObject
import com.googlecode.d2j.dex.Dex2jar
import dalvik.system.DexClassLoader
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset

object FileHelper {
    fun dealInputApkFile(supportedAbi: String, context: Context, apkPath: String): Result<Boolean> {
        val apkFile = File(apkPath)
        val apkName = apkFile.nameWithoutExtension

        FileUtil.mkdir("${apkFile.parent}/${apkName}")

        fun deleteTemp() {
            FileUtil.del("${apkFile.parent}/${apkName}")
            FileUtil.del(apkFile)
            println("执行了")
        }

        // ZipUtil.unzip(apkPath, "${apkFile.parent}/${apkName}") 有的版本会出现zip炸弹的错误，好奇怪

        val apkInner = ZipUtil.toZipFile(apkFile, Charset.defaultCharset())
        val versionPath = "${context.getExternalFilesDir("versions")}/${apkName}/"
        val libFolder = "${apkFile.parent}/${apkName}/lib/${supportedAbi}"
        FileUtil.mkdir(libFolder)

        val abiList = ZipUtil.listFileNames(apkInner, "lib/${supportedAbi}")
        println(abiList.toString())
        if (abiList.isNotEmpty()) {
            abiList.forEach {
                val inputStream = ZipUtil.get(apkInner, "lib/${supportedAbi}/${it}")
                val name = it.split("/").last()
                val libFile = File("${libFolder}/${name}")
                if (!libFile.exists()) { libFile.createNewFile() }
                var i: Int
                val outputStream = FileOutputStream(libFile)
                val buffer = ByteArray(inputStream.available())
                while (inputStream.read(buffer).also { it1 -> i = it1 } != -1){
                    outputStream.write(buffer, 0, i)
                }
                outputStream.flush()
                outputStream.close()
                inputStream.close()
            }
        } else {
            deleteTemp()
            return Result.failure(Exception("这个版本与你的CPU架构不兼容！"))
        }

        val dexInputStream = ZipUtil.get(apkInner, "classes.dex")
        val dexFile = File("${apkFile.parent}/${apkName}/classes.dex")
        if (!dexFile.exists()) { dexFile.createNewFile() }
        var i: Int
        val dexOutput = FileOutputStream(dexFile)
        val buffer = ByteArray(dexInputStream.available())
        while (dexInputStream.read(buffer).also { it2 -> i = it2 } != -1){
            dexOutput.write(buffer, 0, i)
        }
        dexOutput.flush()
        dexOutput.close()
        dexInputStream.close()

        if (!createVersionBaseFile(context, apkName)) {
            deleteTemp()
            return Result.failure(Exception("存在相同版本文件，不可替换！"))
        }

        FileUtil.copy(libFolder, "${versionPath}/", true)
        FileUtil.copy(
            "${apkFile.parent}/${apkName}/classes.dex",
            "${versionPath}/",
            true
        )
        //DexHelper.patchDexFile(context.classLoader, apkPath,"${versionPath}/classes.dex")
        //tryMakeDex2Jar("${versionPath}/classes.dex", "${versionPath}/classes.jar")
        val json = JSONObject()
        json["dexPath"] = "${versionPath}/classes.dex"
        json["gameLib"] = "/${versionPath}/lib"
        FileUtil.writeUtf8String(json.toJSONString(), "${versionPath}/manifest.launcher.json")
        deleteTemp()
        return Result.success(true)
    }

    private fun createVersionBaseFile(context: Context, name: String): Boolean {
        val versionsPath = "${context.getExternalFilesDir("versions")}/"
        val versions = FileUtil.ls(versionsPath)
        if (versions.any { it.name == name }) { return false }
        FileUtil.mkdir("${versionsPath}/${name}")
        return true
    }

    private fun tryMakeDex2Jar(dexPath: String, destination: String) {
        Dex2jar.from(dexPath).to(File(destination).toPath())
    }
}