package com.mojang.minecraftpe.packagesource

interface PackageSourceListener {
    fun onDownloadProgress(paramLong1: Long, paramLong2: Long, paramFloat: Float, paramLong3: Long)

    fun onDownloadStarted()

    fun onDownloadStateChanged(
        paramBoolean1: Boolean,
        paramBoolean2: Boolean,
        paramBoolean3: Boolean,
        paramBoolean4: Boolean,
        paramBoolean5: Boolean,
        paramInt1: Int,
        paramInt2: Int
    )

    fun onMountStateChanged(paramString: String?, paramInt: Int)

    companion object {
        const val DOWNLOADFAILEDREASON_CANCELED: Int = 5

        const val DOWNLOADFAILEDREASON_CONTENTVERIFY_ERROR: Int = 8

        const val DOWNLOADFAILEDREASON_CONTENTVERIFY_MISMATCH: Int = 6

        const val DOWNLOADFAILEDREASON_CONTENTVERIFY_RETRY: Int = 7

        const val DOWNLOADFAILEDREASON_FETCHING_URL: Int = 3

        const val DOWNLOADFAILEDREASON_SDCARD_FULL: Int = 4

        const val DOWNLOADFAILEDREASON_STORAGE_PERMISSION: Int = 1

        const val DOWNLOADFAILEDREASON_UNKNOWN: Int = 0

        const val DOWNLOADFAILEDREASON_UNLICENSED: Int = 2

        const val DOWNLOADPAUSEDREASON_BY_REQUEST: Int = 2

        const val DOWNLOADPAUSEDREASON_NEED_CELLULAR_PERMISSION: Int = 4

        const val DOWNLOADPAUSEDREASON_NEED_WIFI: Int = 6

        const val DOWNLOADPAUSEDREASON_NETWORK_SETUP_FAILURE: Int = 8

        const val DOWNLOADPAUSEDREASON_NETWORK_UNAVAILABLE: Int = 1

        const val DOWNLOADPAUSEDREASON_ROAMING: Int = 7

        const val DOWNLOADPAUSEDREASON_SDCARD_UNAVAILABLE: Int = 9

        const val DOWNLOADPAUSEDREASON_UNKNOWN: Int = 0

        const val DOWNLOADPAUSEDREASON_WIFI_DISABLED: Int = 5

        const val DOWNLOADPAUSEDREASON_WIFI_DISABLED_NEED_CELLULAR_PERMISSION: Int = 3

        const val MOUNTSTATE_ERROR_ALREADY_MOUNTED: Int = 1

        const val MOUNTSTATE_ERROR_COULD_NOT_MOUNT: Int = 2

        const val MOUNTSTATE_ERROR_COULD_NOT_UNMOUNT: Int = 3

        const val MOUNTSTATE_ERROR_INTERNAL: Int = 4

        const val MOUNTSTATE_ERROR_NOT_MOUNTED: Int = 5

        const val MOUNTSTATE_ERROR_PERMISSION_DENIED: Int = 6

        const val MOUNTSTATE_MOUNTED: Int = 7

        const val MOUNTSTATE_UNKNOWN: Int = 0

        const val MOUNTSTATE_UNMOUNTED: Int = 8
    }
}
