package com.hamid.template.ui.loginAndRegister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityRegistrationBinding
import com.hamid.template.ui.dashboard.MainActivity
import com.hamid.template.ui.loginAndRegister.fragments.ForgotPassword
import com.hamid.template.ui.loginAndRegister.fragments.LoginFragment
import com.hamid.template.ui.loginAndRegister.fragments.RegisterFragment
import com.hamid.template.ui.onboarding.OnBoardingActivity
import com.hamid.template.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegistrationBinding, RegisterVM>(), RegisterContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private val registerFragment:RegisterFragment= RegisterFragment()
    private val loginFragment:LoginFragment= LoginFragment()
    private val forgotPassword:ForgotPassword= ForgotPassword()
    private lateinit var currentFrament: Fragment
    private lateinit var fragmentManager: FragmentManager
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        viewModel.initThings()

    }




    override val viewModel: RegisterVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityRegistrationBinding {
        return ActivityRegistrationBinding.inflate(layoutInflater)
    }



    @Override
    override fun setUpView() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.white)
        fragmentManager=supportFragmentManager
        var oldFragment = fragmentManager.findFragmentByTag("loginFragment")
        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit()
        }
        oldFragment = fragmentManager.findFragmentByTag("registerFragment")
        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit()
        }
        oldFragment = fragmentManager.findFragmentByTag("forgotPassword")
        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit()
        }
        currentFrament=loginFragment
        fragmentManager.beginTransaction().add(binding.contentFrame.id,forgotPassword,"forgotPassword").hide(forgotPassword).commit()
        fragmentManager.beginTransaction().add(binding.contentFrame.id,registerFragment,"registerFragment").hide(registerFragment).commit()
        fragmentManager.beginTransaction().add(binding.contentFrame.id,loginFragment,"loginFragment").commit()
    }




    override fun performRegister() {
          fragmentManager.beginTransaction().hide(currentFrament).show(registerFragment).commit()
          currentFrament=registerFragment
    }

    override fun performLogin() {
        fragmentManager.beginTransaction().hide(currentFrament).show(loginFragment).commit()
        currentFrament=loginFragment
    }

    override fun showLoading() {
        showLoader()
    }

    override fun hideLoading() {
        hideLoader()
    }

    @Override
    override fun finishScreen() {
        finish()
    }

    @Override
    override fun setData() {

    }

    override fun onResume() {
        super.onResume()

    }

    override fun moveToUserDetailsFill() {
        startActivity(OnBoardingActivity.getIntent(this))
        finishAffinity()
    }

    override fun moveToForgotPassword() {
        fragmentManager.beginTransaction().hide(currentFrament).show(forgotPassword).commit()
        currentFrament=forgotPassword
    }
    override fun moveToDashboard() {
        startActivity(MainActivity.getIntent(this))
        finishAffinity()
    }

}