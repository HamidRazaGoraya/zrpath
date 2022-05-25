package com.hamid.template.ui.todayTripsList

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.dashboard.models.VisitListModel
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.model.RequestDeleteDocument
import com.hamid.template.ui.todayTripDetails.models.ResponseOnGoingVisit
import com.hamid.template.ui.todayTripsList.models.RequestDashboardAPI
import com.hamid.template.ui.todayTripsList.models.RequestReferralList
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class TodayTripVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<TodayTripContracts>() {

    fun initThings() {
        viewInteractor?.setUpView()
        viewInteractor?.setupAdopter()
    }

    var transportationGroupId=0
    var todayTripResponse:TodayTripResponse?=null
    fun getDataApi()=viewInteractor?.getDataApi()
    fun onBackClick() {
        viewInteractor?.finishScreen()
    }
    fun moveToDetailsActivity(visitListModel: VisitListModel)=viewInteractor?.moveToDetailsActivity(visitListModel)
    fun HideLoading()=viewInteractor?.HideLoading()
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun getDashboard(data: RequestDashboardAPI.Data)=apiRepository.getDashboard(data)

    fun GetReferralListForTransportationGroup(data: RequestReferralList.Data)=apiRepository.GetReferralListForTransportationGroup(data)
    fun getVisitDetails(client: TodayTripResponse.Data.Down.Client,upDown:Boolean)=viewInteractor?.getVisitDetails(client, upDown)
    fun handleTrips(todayTripResponse: TodayTripResponse)=viewInteractor?.handleTrips(todayTripResponse)
    fun loadTripDirection(data: List<TodayTripResponse.Data.Down>)=viewInteractor?.loadTripDirection(data)
    fun getTodayTrips(date:String,facilitiesId: Int)=apiRepository.getNewDashboard(date,facilitiesId)
    fun onGoingVisit(ScheduleID:Int,referralID:Int,transportationGroupID:Int)=apiRepository.OnGoingVisit(ScheduleID,referralID,transportationGroupID)
    fun saveChildSignature(TransportVisitIDValue:Int,ReferralIDValue:Int,ScheduleIDValue:Int,file: File)=apiRepository.saveChildSignature(TransportVisitIDValue, ReferralIDValue, ScheduleIDValue, file)
    fun onDropOfSignatureClicked(visitListModel: VisitListModel)=viewInteractor?.onDropOfSignatureClicked(visitListModel)
    fun showSignatureDialog(data: ResponseOnGoingVisit.Data?,oldSignature:String?)=viewInteractor?.showSignatureDialog(data,oldSignature)
}