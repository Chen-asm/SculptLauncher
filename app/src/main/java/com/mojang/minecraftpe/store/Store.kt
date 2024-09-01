package com.mojang.minecraftpe.store

class Store
    (private val listener: StoreListener) {
    fun acknowledgePurchase(paramString1: String?, paramString2: String?) {}

    fun destructor(){}

    fun getExtraLicenseData(): ExtraLicenseResponseData? = null

    fun getProductSkuPrefix(): String? = null

    fun getRealmsSkuPrefix(): String? = null

    fun getStoreId(): String? = null

    fun hasVerifiedLicense(): Boolean = true

    fun purchase(paramString1: String?, paramBoolean: Boolean, paramString2: String?){}

    fun purchaseGame(){}

    fun queryProducts(paramArrayOfString: Array<String?>?){}

    fun queryPurchases(){}

    fun receivedLicenseResponse(): Boolean = true
}

