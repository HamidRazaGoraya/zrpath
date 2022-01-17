package com.hamid.template.ui.dashboard

import android.os.Bundle
import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.loginAndRegister.logResponseModel.LogInResponse
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

    var tripType:String?=null
    fun showSideMenu()=viewInteractor?.showSideMenu()
    fun hideSideMenu()=viewInteractor?.hideSideMenu()
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()
    var logInResponse:LogInResponse?=null
    fun logOutClicked()=viewInteractor?.logOutClicked()
    fun patientClicked()=viewInteractor?.patientClicked()
    fun mapsClicked()=viewInteractor?.mapsClicked()
    fun settingsClicked()=viewInteractor?.settingsClicked()
    fun helpClicked()=viewInteractor?.helpClicked()
    fun notificationClicked()=viewInteractor?.notificationClicked()
    fun searchClicked()=viewInteractor?.searchClicked()
    fun onButtonBackPressed()=viewInteractor?.onButtonBackPressed()
    fun onFacilitySelected(bundle: Bundle)=viewInteractor?.onFacilitySelected(bundle)
    fun getFacilityDetails(dection:String)=apiRepository.getFacilityDetails(dection)
}