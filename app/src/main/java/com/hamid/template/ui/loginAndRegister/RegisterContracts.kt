package com.hamid.template.ui.loginAndRegister

import com.hamid.template.base.ViewInteractor


interface RegisterContracts : ViewInteractor {
    fun finishScreen()
    fun setData()
    fun setUpView()
    fun performRegister()
    fun performLogin()
    fun moveToUserDetailsFill()
    fun moveToForgotPassword()
}