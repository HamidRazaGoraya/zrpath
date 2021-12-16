package com.hamid.template.ui.onboarding

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<OnBoardingContracts>() {

    fun initThings() {
        viewInteractor?.setUpView()
    }

    fun onBackClick() {
        viewInteractor?.finishScreen()
    }

    fun showFillUserDetails() = viewInteractor?.showFillUserDetails()
    fun moveToDashboard()=viewInteractor?.moveToDashboard()

}