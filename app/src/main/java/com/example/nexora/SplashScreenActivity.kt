package com.example.nexora

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({

            val sharedPref = getSharedPreferences(
                "user_pref",
                MODE_PRIVATE
            )

            val isLogin = sharedPref.getBoolean(
                "isLogin",
                false
            )

            if (isLogin) {

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )

            } else {

                startActivity(
                    Intent(
                        this,
                        AuthActivity::class.java
                    )
                )
            }

            finish()

        }, 2000)
    }
}