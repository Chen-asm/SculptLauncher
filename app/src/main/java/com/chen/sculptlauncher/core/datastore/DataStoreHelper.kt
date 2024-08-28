package com.chen.sculptlauncher.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

// 这个文件用于处理存储键值对数据的逻辑

// 定义keys
val IS_FIRST_OPEN = stringPreferencesKey("applicationCurrentVersion")

// 定义存储通用数据的DataStore
val Context.globalStore: DataStore<Preferences> by preferencesDataStore("globalStore")

// 读取值
inline fun <reified T> Context.getStoredValue(requiredKey: Preferences.Key<T>) = this.globalStore
    .data.map { value -> value[requiredKey] ?: ("-1" as T) }

// 写入值
suspend inline fun <reified T> Context.setStoreValue(key: Preferences.Key<T>, newValue: T) {
    this.globalStore.edit { value ->
        value[key] = newValue
    }
}

