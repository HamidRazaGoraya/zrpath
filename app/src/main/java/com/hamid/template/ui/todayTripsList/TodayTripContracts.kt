package com.hamid.template.ui.todayTripsList

import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.dashboard.models.VisitListModel
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList


interface TodayTripContracts : ViewInteractor {
    fun finishScreen()
    fun setData()
    fun setUpView()
    fun getDataApi()
    fun HideLoading()
    fun ShowLoading()
    fun setupAdopter()
    fun handleTrips(todayTripResponse: TodayTripResponse)
    fun loadTripDirection(data: List<TodayTripResponse.Data.Down>)
    fun moveToDetailsActivity(visitListModel: VisitListModel)
    fun getVisitDetails(client: TodayTripResponse.Data.Down.Client,upDown:Boolean)
}