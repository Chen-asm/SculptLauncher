package com.mojang.minecraftpe.packagesource

class NativePackageSourceListener : PackageSourceListener {
    var mPackageSourceListener: Long = 0L

    external fun nativeOnDownloadProgress(
        paramLong1: Long,
        paramLong2: Long,
        paramLong3: Long,
        paramFloat: Float,
        paramLong4: Long
    )

    external fun nativeOnDownloadStarted(paramLong: Long)

    external fun nativeOnDownloadStateChanged(
        paramLong: Long,
        paramBoolean1: Boolean,
        paramBoolean2: Boolean,
        paramBoolean3: Boolean,
        paramBoolean4: Boolean,
        paramBoolean5: Boolean,
        paramInt1: Int,
        paramInt2: Int
    )

    external fun nativeOnMountStateChanged(paramLong: Long, paramString: String?, paramInt: Int)

    override fun onDownloadProgress(
        paramLong1: Long,
        paramLong2: Long,
        paramFloat: Float,
        paramLong3: Long
    ) {
        nativeOnDownloadProgress(
            this.mPackageSourceListener,
            paramLong1,
            paramLong2,
            paramFloat,
            paramLong3
        )
    }

    override fun onDownloadStarted() {
        nativeOnDownloadStarted(this.mPackageSourceListener)
    }

    override fun onDownloadStateChanged(
        paramBoolean1: Boolean,
        paramBoolean2: Boolean,
        paramBoolean3: Boolean,
        paramBoolean4: Boolean,
        paramBoolean5: Boolean,
        paramInt1: Int,
        paramInt2: Int
    ) {
        nativeOnDownloadStateChanged(
            this.mPackageSourceListener,
            paramBoolean1,
            paramBoolean2,
            paramBoolean3,
            paramBoolean4,
            paramBoolean5,
            paramInt1,
            paramInt2
        )
    }

    override fun onMountStateChanged(paramString: String?, paramInt: Int) {
        nativeOnMountStateChanged(this.mPackageSourceListener, paramString, paramInt)
    }

    fun setListener(paramLong: Long) {
        this.mPackageSourceListener = paramLong
    }
}
