package com.chen.sculptlauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.chen.sculptlauncher.ui.screen.home.HomeScreen
import com.chen.sculptlauncher.ui.theme.SculptLauncherTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        host = this
        setContent {
            SculptLauncherTheme {
                Navigator(screen = HomeScreen){
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .systemBarsPadding(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        CurrentScreen()
                    }
                }
            }
        }
    }

    /**
     * A native method that is implemented by the 'sculptlauncher' native library,
     * which is packaged with this application.
     */
    private external fun stringFromJNI(): String

    companion object {
        // Used to load the 'sculptlauncher' library on application startup.
        init {
            System.loadLibrary("sculptlauncher")
        }
        lateinit var host: MainActivity
    }
}