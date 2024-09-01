package com.mojang.minecraftpe.packagesource

object PackageSourceFactory {
    @JvmStatic
    fun createGooglePlayPackageSource(
        paramString: String?,
        paramPackageSourceListener: PackageSourceListener?
    ): PackageSource {
        return PS()
    }

    class PS: PackageSource() {
        override fun abortDownload() {
        }

        override fun destructor() {
        }

        override fun downloadFiles(
            paramString: String,
            paramLong: Long,
            paramBoolean1: Boolean,
            paramBoolean2: Boolean
        ) {
        }

        override fun getDownloadDirectoryPath(): String {
            return ""
        }

        override fun getMountPath(paramString: String): String {
            return ""
        }

        override fun mountFiles(paramString: String) {
        }

        override fun pauseDownload() {
        }

        override fun resumeDownload() {
        }

        override fun resumeDownloadOnCell() {
        }

        override fun unmountFiles(paramString: String) {
        }
    }
}