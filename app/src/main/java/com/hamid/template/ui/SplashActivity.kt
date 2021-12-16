package com.hamid.template.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.hamid.template.R
import com.hamid.template.ui.dashboard.MainActivity
import com.hamid.template.ui.loginAndRegister.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
                 startActivity(RegisterActivity.getIntent(this))
            finishAffinity()
        }, 100)
    }


}