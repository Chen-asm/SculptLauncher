package com.mojang.minecraftpe

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.Locale
import java.util.regex.Pattern


class HardwareInformation(
    val context: Context,
    private val packageManager: PackageManager,
) {

    init {
        cpuInfo = getCPUInfo()
    }
    private fun appInstalled(paramString: String): Boolean {
        try {
            packageManager.getPackageInfo(paramString, 0)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    private fun checkRootA(): Boolean {
        val bool: Boolean
        val str = Build.TAGS
        bool = str != null && str.contains("test-keys")
        return bool
    }

    private fun checkRootB(): Boolean {
        for (b in 0..9) {
            arrayOfNulls<String?>(10)[0] = "/sbin/su"
            arrayOfNulls<String?>(10)[1] = "/system/bin/su"
            arrayOfNulls<String?>(10)[2] = "/system/xbin/su"
            arrayOfNulls<String?>(10)[3] = "/data/local/xbin/su"
            arrayOfNulls<String?>(10)[4] = "/data/local/bin/su"
            arrayOfNulls<String?>(10)[5] = "/system/sd/xbin/su"
            arrayOfNulls<String?>(10)[6] = "/system/bin/failsafe/su"
            arrayOfNulls<String?>(10)[7] = "/system/app/Superuser.apk"
            arrayOfNulls<String?>(10)[8] = "/data/local/su"
            arrayOfNulls<String?>(10)[9] = "/su/bin/su"
            if (arrayOfNulls<String?>(10)[b.toInt()]?.let {
                    File(it)
                        .exists()
                } == true
            ) return true
        }
        return false
    }

    private fun checkRootC(): Boolean {
        for (b in 0..1) {
            arrayOfNulls<String?>(2)[0] = "eu.chainfire.supersu"
            arrayOfNulls<String?>(2)[1] = "eu.chainfire.supersu.pro"
            if (appInstalled(arrayOfNulls<String?>(2)[b]!!)) return true
        }
        return false
    }

    fun getLocale(): String {
        return Locale.getDefault().toString()
    }

    fun getInstallerPackageName(): String? {
        val str: String?
        val packageManager = this.packageManager
        str = packageManager.getInstallerPackageName(context.packageName)
        return str
    }

    fun getIsRooted(): Boolean {
        return (checkRootA() || checkRootB() || checkRootC())
    }

    @SuppressLint("HardwareIds")
    fun getSecureId(): String {
        return Settings.Secure.getString(context.contentResolver, "android_id")
    }

    fun getSignaturesHashCode(): Int {
        var bool: Int = 0
        var b: Int = 0
        try {
            val arrayOfSignature = (this.packageManager.getPackageInfo(this.context.packageName, 64)).signatures
            val j = arrayOfSignature.size
            var i = 0
            while (true){
                bool = i
                if (b < j){
                    try {
                        bool = arrayOfSignature[b].hashCode()
                        i = i xor bool
                        b++
                    } catch (_: Exception){}
                    continue
                }
                break
            }
        } catch (e: Exception){
            val bool1 = false
            e.printStackTrace()
            bool = bool1.hashCode()
        }
        return bool
    }

    private fun getCPUInfo(): CPUInfo {
        val hashMap = HashMap<String, String>()
        val bool = File("/proc/cpuinfo").exists()
        val b3: Byte = 0
        var b1: Byte = 0
        val b2: Byte = 0
        if (bool) {
            var b = b3
            try {
                val bufferedReader: BufferedReader = BufferedReader(FileReader(File("/proc/cpuinfo")))
                val pattern = Pattern.compile("(\\w*)\\s*:\\s([^\\n]*)")
                b1 = b2
                while (true) {
                    b = b1
                    val str = bufferedReader.readLine()
                    if (str != null) {
                        b = b1
                        val matcher= pattern.matcher(str)
                        b = b1
                        if (!matcher.find()) continue
                        b = b1
                        if (matcher.groupCount() === 2) {
                            if (!hashMap.containsKey(matcher.group(1))) {
                                hashMap[matcher.group(1)] = matcher.group(2)
                            }
                            if (matcher.group(1).contentEquals("processor")) b1++
                        }
                        continue
                    }
                    b = b1
                    bufferedReader.close()
                    break
                }
            } catch (exception: java.lang.Exception) {
                exception.printStackTrace()
                b1 = b
            }
        }
        return CPUInfo(hashMap, b1.toInt())
    }
    inner class CPUInfo(private val cpuLines: Map<String, String>, val numberCPUCores: Int) {
        fun getCPULine(param1String: String): String? {
            return if (cpuLines.containsKey(param1String)) cpuLines[param1String] else ""
        }
    }

    companion object {
        lateinit var cpuInfo: CPUInfo
        @JvmStatic
        fun getDeviceModelName(): String {
            var str1 = Build.MANUFACTURER
            val str2 = Build.MODEL
            if (str2.startsWith(str1)) {
                str1 = str2.uppercase(Locale.getDefault())
            } else {
                val stringBuilder = java.lang.StringBuilder()
                stringBuilder.append(str1.uppercase(Locale.getDefault()))
                stringBuilder.append(" ")
                stringBuilder.append(str2)
                str1 = stringBuilder.toString()
            }
            return str1
        }

        @JvmStatic
        fun getAndroidVersion(): String {
            val stringBuilder = StringBuilder()
            stringBuilder.append("Android ")
            stringBuilder.append(Build.VERSION.RELEASE)
            return stringBuilder.toString()
        }

        @JvmStatic
        fun getCPUType(): String {
            return Build.SUPPORTED_ABIS[0]
        }

        @JvmStatic
        fun getCPUName(): String {
            return cpuInfo.getCPULine("Hardware") ?: ""
        }

        @JvmStatic
        fun getCPUFeatures(): String {
            return cpuInfo.getCPULine("Features") ?: ""
        }

        @JvmStatic
        fun getNumCores(): Int {
            return cpuInfo.numberCPUCores
        }

        @JvmStatic
        @SuppressLint("HardwareIds")
        fun getSerialNumber(): String {
            return Build.SERIAL
        }

        @JvmStatic
        fun getBoard(): String {
            return Build.BOARD
        }
    }
}