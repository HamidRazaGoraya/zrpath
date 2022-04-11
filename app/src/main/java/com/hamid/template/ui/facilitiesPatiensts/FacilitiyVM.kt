package com.hamid.template.ui.facilitiesPatiensts

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.facilitiesPatiensts.models.RequestTransportDetails
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.model.RequestSaveForm
import com.hamid.template.ui.fillForm.model.RequestSavedDocumentList
import com.hamid.template.ui.mapScreen.models.RequestDocumentUrl
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FacilitiyVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<FacilityContracts>() {

    fun initThings() {
        viewInteractor?.setData()
    }
    var responseDocumentList :ResponseDocumentList?=null
    lateinit var facility: AllFacilitiesModel.Data
    fun showPatientsList()=viewInteractor?.showPatientsList()
    fun onButtonBackPressed()=viewInteractor?.onButtonBackPressed()
    fun getTodayTrips(date:String)=apiRepository.getTodayTrip(date,facility.facilityID)
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()
    fun startMapActivity(client: TodayTripResponse.Data.Down.Client)=viewInteractor?.startMapActivity(client)



    fun apiCallForUrl(form: ResponseDocumentList.DataItem, client: TodayTripResponse.Data.Down.Client)=viewInteractor?.apiCallForUrl(form,client)
    fun showSelectFormDialog(documentList: ResponseDocumentList, client: TodayTripResponse.Data.Down.Client)=viewInteractor?.showSelectFormDialog(documentList,client)
    fun getFormsList(clicked:Boolean,client: TodayTripResponse.Data.Down.Client)=viewInteractor?.getFormsList(clicked,client)
    fun onCheckListClicked()=viewInteractor?.onCheckListClicked()
    fun getUserCheckList(ReferralID:Int,TripDirection:String)=apiRepository.getUserCheckList(ReferralID,TripDirection)
    fun getDocumentList()=apiRepository.getDocumentList()
    fun getDocumentUrl(data: RequestDocumentUrl.Data)=apiRepository.getDocumentUrl(data)

    fun getTripDetails(data:RequestTransportDetails.Data)=apiRepository.requestTransportDetails(data)

    fun requestTransportDetails(data: RequestTransportDetails.Data)=apiRepository.requestTransportDetails(data)

    fun requestSaveForm(data:RequestSaveForm.Data)=apiRepository.requestSaveForm(data)

    fun moveToTodayTrips(group: TodayTripResponse.Data.Down)=viewInteractor?.moveToTodayTrips(group)
}