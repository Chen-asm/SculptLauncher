package com.mojang.minecraftpe.store

class NativeStoreListener
    (var mStoreListener: Long) : StoreListener {
    external fun onPurchaseCanceled(paramLong: Long, paramString: String?)

    override fun onPurchaseCanceled(paramString: String?) {
        onPurchaseCanceled(this.mStoreListener, paramString)
    }

    external fun onPurchaseFailed(paramLong: Long, paramString: String?)

    override fun onPurchaseFailed(paramString: String?) {
        onPurchaseFailed(this.mStoreListener, paramString)
    }

    external fun onPurchaseSuccessful(paramLong: Long, paramString: String?)

    override fun onPurchaseSuccessful(paramString: String?) {
        onPurchaseSuccessful(this.mStoreListener, paramString)
    }

    override fun onQueryProductsFail() {
        onQueryProductsFail(this.mStoreListener)
    }

    external fun onQueryProductsFail(paramLong: Long)

    external fun onQueryProductsSuccess(paramLong: Long, paramArrayOfProduct: Array<Product?>?)

    override fun onQueryProductsSuccess(paramArrayOfProduct: Array<Product?>?) {
        onQueryProductsSuccess(this.mStoreListener, paramArrayOfProduct)
    }

    override fun onQueryPurchasesFail() {
        onQueryPurchasesFail(this.mStoreListener)
    }

    external fun onQueryPurchasesFail(paramLong: Long)

    external fun onQueryPurchasesSuccess(paramLong: Long, paramArrayOfPurchase: Array<Purchase?>?)

    override fun onQueryPurchasesSuccess(paramArrayOfPurchase: Array<Purchase?>?) {
        onQueryPurchasesSuccess(this.mStoreListener, paramArrayOfPurchase)
    }

    external fun onStoreInitialized(paramLong: Long, paramBoolean: Boolean)

    override fun onStoreInitialized(paramBoolean: Boolean) {
        onStoreInitialized(this.mStoreListener, paramBoolean)
    }
}

