package com.hamid.template.ui.dashboard

import android.location.Location
import android.os.Bundle
import androidx.lifecycle.LiveData
import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.network.ErrorMessage
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.facilitiesPatiensts.models.RequestDeleteCheck
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSaveCheck
import com.hamid.template.ui.fillForm.model.RequestDeleteDocument
import com.hamid.template.ui.fillForm.model.RequestSavedDocumentList
import com.hamid.template.ui.fillForm.model.RequestSavedOpenForm
import com.hamid.template.ui.loginAndRegister.logResponseModel.LogInResponse
import com.hamid.template.ui.todayTripsList.models.RequestReferralList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject
constructor(private val apiRepository: ApiRepository, ) : BaseViewModel<MainContracts>() {

    fun setData(){
        viewInteractor?.initiate()
    }

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
    fun getFacilityDetails()=apiRepository.getFacilityDetails()
    fun getSavedDocuments(data: RequestSavedDocumentList.Data)=apiRepository.getSavedDocumentsList(data)
    fun openSavedForm(data: RequestSavedOpenForm.Data)=apiRepository.openSavedForm(data)
    fun deleteDocument(data:RequestDeleteDocument.Data)=apiRepository.deleteDocument(data)

    fun moveToTodayTrip()=viewInteractor?.moveToTodayTrip()

    fun getDashboard()=apiRepository.getDashboard()


}