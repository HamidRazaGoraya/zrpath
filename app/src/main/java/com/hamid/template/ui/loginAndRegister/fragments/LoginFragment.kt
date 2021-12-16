package com.hamid.template.ui.loginAndRegister.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.hamid.template.base.BaseFragment
import com.hamid.template.databinding.LoginFragmentBinding
import com.hamid.template.ui.dashboard.MainVM
import com.hamid.template.ui.loginAndRegister.RegisterVM
import com.hamid.template.ui.onboarding.OnBoardingActivity
import com.hamid.template.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding, RegisterVM>() {

    override val viewModel: RegisterVM by activityViewModels()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager


    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): LoginFragmentBinding {
        return LoginFragmentBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         setUpListners()
    }

    private fun setUpListners() {
        binding.createNewAccount.setOnClickListener {
            viewModel.onRegisterClick()
        }
        binding.LogInButton.setOnClickListener {
            viewModel.moveToUserDetailsFill()
        }
    }

    override fun onResume() {
        super.onResume()
    }

}