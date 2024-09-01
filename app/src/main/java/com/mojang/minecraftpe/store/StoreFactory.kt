package com.mojang.minecraftpe.store

object StoreFactory {
    @JvmStatic
    fun createAmazonAppStore(paramStoreListener: StoreListener?, v2: Boolean): Store {
        return Store(paramStoreListener!!)
    }

    @JvmStatic
    fun createGooglePlayStore(s: String?, listener: StoreListener?): Store {
        return Store(listener!!)
    }

    @JvmStatic
    fun createSamsungAppStore(storeListener: StoreListener?): Store {
        return Store(storeListener!!)
    }
}

