package com.mojang.minecraftpe.store

interface StoreListener {
    fun onPurchaseCanceled(paramString: String?)

    fun onPurchaseFailed(paramString: String?)

    fun onPurchaseSuccessful(paramString: String?)

    fun onQueryProductsFail()

    fun onQueryProductsSuccess(paramArrayOfProduct: Array<Product?>?)

    fun onQueryPurchasesFail()

    fun onQueryPurchasesSuccess(paramArrayOfPurchase: Array<Purchase?>?)

    fun onStoreInitialized(paramBoolean: Boolean)
}

