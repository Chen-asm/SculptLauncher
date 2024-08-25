package com.mojang.minecraftpe

import android.os.Build
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.Locale
import java.util.regex.Pattern

object HardwareInformation {
    private val cpuInfo: CPUInfo = getCPUInfo()
    fun getAndroidVersion(): String = "Android ${Build.VERSION.RELEASE}"
    fun getCPUFeatures(): String = cpuInfo.getCPULine("Features")
    fun getCPUName(): String = cpuInfo.getCPULine("getCPUName")
    fun getCPUType(): String = Build.SUPPORTED_ABIS.toString()
    fun getLocale(): String = Locale.getDefault().toString()
    fun getNumCores(): Int = cpuInfo.getNumberCPUCores()

    fun getDeviceModelName(): String {
        val str1 = Build.MANUFACTURER
        val str2 = Build.MODEL
        if (str2.startsWith(str1)) {
            return str2.uppercase(Locale.getDefault())
        }
        return str1.uppercase(Locale.getDefault()) + " " + str2
    }

    private fun getCPUInfo(): CPUInfo {
        val localHashMap = HashMap<String, String>()
        var i = 0
        if (!File("/proc/cpuinfo").exists()) {
            return CPUInfo(localHashMap, 0)
        }
        val localBufferedReader = BufferedReader(FileReader(File("/proc/cpuinfo")))
        val localPattern: Pattern = Pattern.compile("(\\w*)\\s*:\\s([^\\n]*)")
        while (true) {
            var localObject: Any = localBufferedReader.readLine()
            localObject = localPattern.matcher(localObject as CharSequence)
            if (localObject.find()) {
                if (localObject.groupCount() == 2) {
                    if (!localHashMap.containsKey(localObject.group(1)!!)) {
                        localHashMap[localObject.group(1)!!] = localObject.group(2)!!
                    }
                    if (localObject.group(1)?.contentEquals("processor") == true) {
                        i += 1
                    }
                }
            } else {
                break
            }
        }
        localBufferedReader.close()
        return CPUInfo(localHashMap, i)
    }

    class CPUInfo(private val paramMap: Map<String, String>, private val paramInt: Int) {
        private var cpuLines: Map<String, String> = paramMap
        private var numberCPUCores: Int = paramInt

        fun getCPULine(paramString: String): String {
            return if (cpuLines.containsKey(paramString)) {
                cpuLines[paramString]!!
            } else {
                ""
            }
        }

        fun getNumberCPUCores(): Int = numberCPUCores
    }
}