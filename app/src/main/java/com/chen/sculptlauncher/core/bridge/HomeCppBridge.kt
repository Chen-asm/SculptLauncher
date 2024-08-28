package com.chen.sculptlauncher.core.bridge

/**
 * HomeScreen 用于与cpp代码沟通的桥
 */
object HomeCppBridge {
    external fun fetchSOAbi(soFilePath: String): String
}