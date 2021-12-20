package com.hamid.template.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.hamid.template.R
import com.hamid.template.ui.dashboard.MainActivity
import com.hamid.template.ui.loginAndRegister.RegisterActivity
import com.hamid.template.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
                 if (sharedPreferenceManager.UserLogInResponse==null){
                     startActivity(RegisterActivity.getIntent(this))
                 }else{
                     startActivity(MainActivity.getIntent(this))
                 }
            finishAffinity()
        }, 100)
    }


}