package com.hamid.template.ui.checkList

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckListVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<CheckListContracts>() {

    fun initThings() {
        viewInteractor?.setData()
        viewInteractor?.checkForCheckList()
    }
    lateinit var client: TodayTripResponse.Data.Down.Client
    fun onButtonBackPressed()=viewInteractor?.onButtonBackPressed()
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()

    fun getUserCheckList(ScheduleID:Int,ReferralID:Int)=apiRepository.getUserCheckList(ScheduleID, ReferralID)
}