package com.chen.sculptlauncher.ui.screen.versions

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.documentfile.provider.DocumentFile
import androidx.window.core.layout.WindowSizeClass
import cafe.adriel.voyager.core.screen.Screen
import com.chen.sculptlauncher.core.file.FileHelper
import com.chen.sculptlauncher.core.file.UriFileHelper
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object InstallVersionScreen : Screen {
    private fun readResolve(): Any = InstallVersionScreen

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val fileImporter = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { selected ->
            if (selected != null) {
                val copyResult = UriFileHelper.copyFile2Private(context, selected)
                copyResult.onSuccess { value: String ->
                    FileHelper.dealInputApkFile(
                        Build.SUPPORTED_ABIS[0], context, value
                    ).onSuccess {
                        Toast.makeText(context, "完成", Toast.LENGTH_SHORT).show()
                    }.onFailure {
                        Toast.makeText(context, it.message ?: "null", Toast.LENGTH_SHORT).show()
                    }
                }.onFailure {
                    Toast.makeText(context, it.message ?: "null", Toast.LENGTH_SHORT).show()
                }
            }
        }
        InstallVersionContent {
            println(Build.SUPPORTED_ABIS.toList().toString())
            fileImporter.launch("application/vnd.android.package-archive")
        }
    }

    @Composable private fun InstallVersionContent(
        windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
        openFile: () -> Unit
    ){
        Button(onClick = openFile) {
            Text(text = "测试")
        }
    }
}