package com.hamid.template.ui.mapScreen

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<MapContracts>() {

    fun initThings() {
        viewInteractor?.setData()
        viewInteractor?.getFormsList(false)
    }
    var responseDocumentList :ResponseDocumentList?=null
    lateinit var client: TodayTripResponse.Data.Down.Client
    fun onButtonBackPressed()=viewInteractor?.onButtonBackPressed()
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()
    fun fillUserDetails()=viewInteractor?.fillUserDetails(client)
    fun checkForCheckList()=viewInteractor?.checkForCheckList()
    fun forloop(){
        val numbers = listOf(1, 2, 3)
        for (num in numbers.listIterator()) {
            println(num)
        }
    }
    fun apiCallForUrl(form:ResponseDocumentList.DataItem)=viewInteractor?.apiCallForUrl(form)
    fun showSelectFormDialog(documentList: ResponseDocumentList)=viewInteractor?.showSelectFormDialog(documentList)
    fun getFormsList(clicked:Boolean)=viewInteractor?.getFormsList(clicked)
    fun onCheckListClicked()=viewInteractor?.onCheckListClicked()
    fun getUserCheckList(ScheduleID:Int,ReferralID:Int)=apiRepository.getUserCheckList(ScheduleID, ReferralID)
    fun getDocumentList()=apiRepository.getDocumentList()
    fun getDocumentUrl(EBFormID:String,ReferralID:Int,SavePreference:Boolean)=apiRepository.getDocumentUrl(EBFormID, ReferralID, SavePreference)
}