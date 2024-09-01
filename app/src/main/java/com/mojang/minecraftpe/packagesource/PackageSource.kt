package com.mojang.minecraftpe.packagesource

import java.util.EnumMap
import android.util.Log

abstract class PackageSource {
    companion object {
        private val stringMap: EnumMap<StringResourceId, String> = EnumMap(StringResourceId::class.java)

        fun getStringResource(paramStringResourceId: StringResourceId): String {
            return stringMap[paramStringResourceId] ?: run {
                Log.e("PackageSource", "getStringResource - id: ${paramStringResourceId.name} is not set.")
                paramStringResourceId.name
            }
        }

        @JvmStatic
        fun setStringResource(paramInt: Int, paramString: String) {
            setStringResource(StringResourceId.fromInt(paramInt), paramString)
        }

        fun setStringResource(paramStringResourceId: StringResourceId, paramString: String) {
            if (paramStringResourceId in stringMap) {
                Log.w("PackageSource", "setStringResource - id: ${paramStringResourceId.name} already set.")
            }
            stringMap[paramStringResourceId] = paramString
        }
    }

    abstract fun abortDownload()
    abstract fun destructor()
    abstract fun downloadFiles(paramString: String, paramLong: Long, paramBoolean1: Boolean, paramBoolean2: Boolean)
    abstract fun getDownloadDirectoryPath(): String
    abstract fun getMountPath(paramString: String): String
    abstract fun mountFiles(paramString: String)
    abstract fun pauseDownload()
    abstract fun resumeDownload()
    abstract fun resumeDownloadOnCell()
    abstract fun unmountFiles(paramString: String)

    enum class StringResourceId constructor(internal val value: Int) {
        STATE_UNKNOWN(0),
        STATE_IDLE(0),
        STATE_FETCHING_URL(2),
        STATE_CONNECTING(3),
        STATE_DOWNLOADING(4),
        STATE_COMPLETED(5),
        STATE_PAUSED_NETWORK_UNAVAILABLE(6),
        STATE_PAUSED_NETWORK_SETUP_FAILURE(7),
        STATE_PAUSED_BY_REQUEST(8),
        STATE_PAUSED_WIFI_UNAVAILABLE(9),
        STATE_PAUSED_WIFI_DISABLED(10),
        STATE_PAUSED_ROAMING(11),
        STATE_PAUSED_SDCARD_UNAVAILABLE(12),
        STATE_FAILED_UNLICENSED(13),
        STATE_FAILED_FETCHING_URL(14),
        STATE_FAILED_SDCARD_FULL(15),
        STATE_FAILED_CANCELLED(16),
        STATE_FAILED(17),
        KILOBYTES_PER_SECOND(18),
        TIME_REMAINING_NOTIFICATION(19),
        NOTIFICATIONCHANNEL_NAME(20),
        NOTIFICATIONCHANNEL_DESCRIPTION(21);

        companion object {
            fun fromInt(paramInt: Int): StringResourceId {
                return entries.firstOrNull { it.value == paramInt } ?: throw IllegalArgumentException("Invalid value")
            }
        }
    }
}
