package com.hamid.template.ui.todayTripsList

import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList


interface TodayTripContracts : ViewInteractor {
    fun finishScreen()
    fun setData()
    fun setUpView()
    fun getDataApi()
    fun HideLoading()
    fun ShowLoading()
    fun setupAdopter()
    fun moveToDetailsActivity(item: ResponseDashBoard.Data.VisitItem)
}