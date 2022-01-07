package com.hamid.template.ui.loginAndRegister

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<RegisterContracts>() {

    fun initThings() {
        viewInteractor?.setUpView()
    }

    fun onBackClick() {
        viewInteractor?.finishScreen()
    }
    fun showLoading()=viewInteractor?.showLoading()
    fun hideLoading()=viewInteractor?.hideLoading()

    fun onRegisterClick() = viewInteractor?.performRegister()
    fun onLoginClick()=viewInteractor?.performLogin()
    fun moveToUserDetailsFill()=viewInteractor?.moveToUserDetailsFill()
    fun moveToForgotPassword()=viewInteractor?.moveToForgotPassword()
    fun moveToDashboard()=viewInteractor?.moveToDashboard()

    fun signInUser(userName:String,pasword:String) = apiRepository.signInUser(userName,pasword)
}