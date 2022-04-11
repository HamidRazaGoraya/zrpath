package com.hamid.template.ui.todayTripsList

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.fillForm.model.RequestDeleteDocument
import com.hamid.template.ui.todayTripsList.models.RequestReferralList
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getDataApi()=viewInteractor?.getDataApi()
    fun onBackClick() {
        viewInteractor?.finishScreen()
    }
    fun moveToDetailsActivity(item: ResponseDashBoard.Data.VisitItem)=viewInteractor?.moveToDetailsActivity(item)
    fun HideLoading()=viewInteractor?.HideLoading()
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun getDashboard()=apiRepository.getDashboard()

    fun GetReferralListForTransportationGroup(data: RequestReferralList.Data)=apiRepository.GetReferralListForTransportationGroup(data)
}