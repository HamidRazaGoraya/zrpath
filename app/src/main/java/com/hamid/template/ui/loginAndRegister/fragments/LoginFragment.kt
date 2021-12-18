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
import com.hamid.template.ui.loginAndRegister.models.LogInRequest
import com.hamid.template.ui.loginAndRegister.models.LogInResponse
import com.hamid.template.ui.onboarding.OnBoardingActivity
import com.hamid.template.utils.Resource
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
            if (varified()){
                viewModel.signInUser(LogInRequest(true,true,binding.userName.text.toString(),binding.pin.text.toString()))
                    .observe(viewLifecycleOwner){
                        when (it.status) {
                            Resource.Status.SUCCESS -> {
                                viewModel.hideLoading()
                                it.data?.let { it1 ->
                                    HandleLogIn(it1)
                                }
                            }
                            Resource.Status.ERROR -> {
                                viewModel.hideLoading()
                                it.message?.let { it1 -> showSnackBar(it1) }
                            }
                            Resource.Status.LOADING -> {
                                viewModel.showLoading()
                            }
                        }
                    }
            }
        }
        binding.forgotPassword.setOnClickListener {
            viewModel.moveToForgotPassword()
        }
    }

    private fun HandleLogIn(it1: LogInResponse) {
        if (it1.isSuccess){
            if (it1.data.sessionValueData.isTempPassword){
                viewModel.moveToUserDetailsFill()
            }else{
                viewModel.moveToDashboard()
            }
        }else{
            it1.message?.let {
                showSnackBar(it)
            }
        }
    }

    private fun varified(): Boolean {
        if (binding.userName.text.isNullOrEmpty() || binding.userName.text.toString().replace(" ","").length<3){
         binding.userName.error="Too short"
            return false
        }
        if (binding.pin.text.isNullOrEmpty() || binding.pin.text.toString().replace(" ","").length<3){
            binding.pin.error="Too short"
            return false
        }
        return true

    }


    override fun onResume() {
        super.onResume()
    }

}