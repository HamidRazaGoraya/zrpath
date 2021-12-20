package com.hamid.template.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityOnboardingBinding
import com.hamid.template.databinding.ActivityRegistrationBinding
import com.hamid.template.ui.dashboard.MainActivity
import com.hamid.template.ui.loginAndRegister.fragments.LoginFragment
import com.hamid.template.ui.loginAndRegister.fragments.RegisterFragment
import com.hamid.template.ui.onboarding.fragments.FillUserDetails
import com.hamid.template.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding, OnBoardingVM>(), OnBoardingContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private val fillUserDetails:FillUserDetails= FillUserDetails()
    private lateinit var currentFrament: Fragment
    private lateinit var fragmentManager: FragmentManager
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, OnBoardingActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        viewModel.initThings()

    }




    override val viewModel: OnBoardingVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityOnboardingBinding {
        return ActivityOnboardingBinding.inflate(layoutInflater)
    }



    @Override
    override fun setUpView() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.white)
        fragmentManager=supportFragmentManager
        var oldFragment = fragmentManager.findFragmentByTag("fillUserDetails")
        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit()
        }
        currentFrament=fillUserDetails
        fragmentManager.beginTransaction().add(binding.contentFrame.id,fillUserDetails,"fillUserDetails").commit()
    }


    override fun showFillUserDetails() {
        fragmentManager.beginTransaction().hide(currentFrament).show(fillUserDetails).commit()
        currentFrament=fillUserDetails
    }



    fun showLoading() {
        showLoader()
    }

    fun hideLoading() {
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

    override fun moveToDashboard() {
        startActivity(MainActivity.getIntent(this))
        finishAffinity()
    }



}