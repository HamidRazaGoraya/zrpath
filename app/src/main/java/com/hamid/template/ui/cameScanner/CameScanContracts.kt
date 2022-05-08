package com.hamid.template.ui.cameScanner

import com.hamid.template.base.ViewInteractor


interface CameScanContracts : ViewInteractor {
    fun finishScreen()
    fun setData()
    fun setUpView()

}