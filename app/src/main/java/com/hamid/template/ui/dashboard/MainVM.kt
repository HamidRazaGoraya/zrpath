package com.hamid.template.ui.dashboard

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.dashboard.entery.RequestUserCv
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<MainContracts>() {

    fun setData(){
        viewInteractor?.initiate()
    }
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()
    fun generateCV(requestUserCv: RequestUserCv) = apiRepository.generateCv(requestUserCv)

}