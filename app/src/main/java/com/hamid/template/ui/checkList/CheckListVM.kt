package com.hamid.template.ui.checkList

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.checkList.models.RequestSelfCheckList
import com.hamid.template.ui.facilitiesPatiensts.models.RequestDeleteCheck
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSaveCheck
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.mapScreen.models.RequestDocumentUrl
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckListVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<CheckListContracts>() {

    fun initThings(client:TodayTripResponse.Data.Down.Client) {
        viewInteractor?.setData()
        viewInteractor?.checkForCheckList()
    }
    var responseDocumentList :ResponseDocumentList?=null
    lateinit var client: TodayTripResponse.Data.Down.Client
    fun onButtonBackPressed()=viewInteractor?.onButtonBackPressed()
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()
    fun checkForCheckList()=  viewInteractor?.checkForCheckList()
    fun getUserCheckList(ReferralID:Int,TripDirection:String)=apiRepository.getUserCheckList(ReferralID,TripDirection)
    fun checkCheckList(data: RequestSaveCheck.Data)=apiRepository.getSaveCheckPoint(data)
    fun unCheckList(data: RequestDeleteCheck.Data)=apiRepository.deleteCheckList(data)

    fun apiCallForUrl(checK: UserCheckListResponse.Data.CheckListItem, client: TodayTripResponse.Data.Down.Client)=viewInteractor?.apiCallForUrl(checK,client)


    fun getDocumentList()=apiRepository.getDocumentList()
    fun getDocumentUrl(data: RequestDocumentUrl.Data)=apiRepository.getDocumentUrl(data)
    fun checkListCompleted(data: RequestSelfCheckList.Data)=apiRepository.checkListCompleted(data)
    fun checkListCompleted()=viewInteractor?.checkListCompleted()
}