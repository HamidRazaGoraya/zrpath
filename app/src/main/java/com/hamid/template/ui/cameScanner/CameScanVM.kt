package com.hamid.template.ui.cameScanner

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameScanVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<CameScanContracts>() {

    fun initThings() {
        viewInteractor?.setUpView()
    }

    fun onBackClick() {
        viewInteractor?.finishScreen()
    }



}