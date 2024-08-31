package com.chen.sculptlauncher.core.file

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object UriFileHelper {
    /**
     * 将Uri指向的文件复制到沙盒中
     * @param context 上下文
     * @param selected 选中的文件
     * @return Result<String> kt结果类
     */
    fun copyFile2Private(context: Context, selected: Uri): Result<String> {
        val doc = DocumentFile.fromSingleUri(context, selected)
        val path = "${context.getExternalFilesDir("apks")}/${doc!!.name!!}"
        val file = File(path)
        if (!file.exists()) { file.createNewFile() }
        val destination = DocumentFile.fromFile(file)
        try {
            val pfd = context.contentResolver.openFileDescriptor(selected, "r")
            if (pfd != null) {
                val inputStream = FileInputStream(pfd.fileDescriptor)
                val pfdOutput = context.contentResolver.openFileDescriptor(destination.uri, "w")
                if (pfdOutput != null) {
                    val outputStream = FileOutputStream(pfdOutput.fileDescriptor)
                    val buffer = ByteArray(inputStream.available())
                    var read: Int
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        outputStream.write(buffer, 0, read)
                    }
                    outputStream.flush()
                    outputStream.close()
                    inputStream.close()
                    pfdOutput.close()
                    pfd.close()
                    return Result.success(path)
                } else {
                    return Result.failure(Exception("输出流异常"))
                }
            } else {
                return Result.failure(Exception("输入文件异常"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}