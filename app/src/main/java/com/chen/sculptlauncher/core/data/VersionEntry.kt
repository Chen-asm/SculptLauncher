package com.chen.sculptlauncher.core.data

/**
 * 规定一个游戏版本的数据实体
 */
data class VersionEntry(
    val path: String,
    val manifestPath: String,
    val gameArch: String
)
