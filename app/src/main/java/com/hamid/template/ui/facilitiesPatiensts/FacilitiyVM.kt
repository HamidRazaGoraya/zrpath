package com.hamid.template.ui.facilitiesPatiensts

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
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
    fun getUserCheckList(ScheduleID:Int,ReferralID:Int)=apiRepository.getUserCheckList(ScheduleID, ReferralID)
    fun getDocumentList()=apiRepository.getDocumentList()
    fun getDocumentUrl(EBFormID:String,ReferralID:Int,SavePreference:Boolean)=apiRepository.getDocumentUrl(EBFormID, ReferralID, SavePreference)

}