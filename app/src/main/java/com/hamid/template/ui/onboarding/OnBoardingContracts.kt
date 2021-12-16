package com.hamid.template.ui.onboarding

import com.hamid.template.base.ViewInteractor


interface OnBoardingContracts : ViewInteractor {
    fun finishScreen()
    fun setData()
    fun setUpView()
    fun showFillUserDetails()
    fun moveToDashboard()
}