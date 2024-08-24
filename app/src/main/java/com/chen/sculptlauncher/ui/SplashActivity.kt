package com.chen.sculptlauncher.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chen.sculptlauncher.MainActivity
import com.chen.sculptlauncher.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 延时跳转
        binding.root.postDelayed({
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish() // 推出 activities 堆栈
        }, 2000)
    }
}

/**
 * /sdcard/Android/data/包名/files/minecraftVersions/版本名
 * minecraftpe.so && fmod.so | launcher_version.json
 *
 * */