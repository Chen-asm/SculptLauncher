package com.mojang.minecraftpe.store

class ExtraLicenseResponseData(paramLong1: Long, paramLong2: Long, paramLong3: Long) {
    var retryAttempts: Long = 0L

    var retryUntilTime: Long = 0L

    var validationTime: Long = 0L

    init {
        this.validationTime = paramLong1
        this.retryUntilTime = paramLong2
        this.retryAttempts = paramLong3
    }
}
