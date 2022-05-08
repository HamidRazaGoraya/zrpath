package com.hamid.template.ui.cameScanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hamid.template.R
import com.hamid.template.base.BaseActivity
import com.hamid.template.databinding.ActivityCameScanBinding
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
class CameScanActivity : BaseActivity<ActivityCameScanBinding, CameScanVM>(), CameScanContracts {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager


    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CameScanActivity::class.java)
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.viewInteractor = this
        viewModel.initThings()

    }




    override val viewModel: CameScanVM by viewModels()

    @Override
    override fun setBinding(layoutInflater: LayoutInflater): ActivityCameScanBinding {
        return ActivityCameScanBinding.inflate(layoutInflater)
    }



    @Override
    override fun setUpView() {
        window.setNavigationBarColor(resources.getColor(R.color.white))
        window.statusBarColor=resources.getColor(R.color.white)
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


}