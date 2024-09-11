package com.example.crud_to_do_it.Screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lifecycleScope.launch {

            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}
