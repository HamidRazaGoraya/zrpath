package com.hamid.template.ui.facilitiesPatiensts

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
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
    lateinit var facility: AllFacilitiesModel.Data
    fun showPatientsList()=viewInteractor?.showPatientsList()
    fun onButtonBackPressed()=viewInteractor?.onButtonBackPressed()
    fun getTodayTrips(date:String)=apiRepository.getTodayTrip(date,facility.facilityID)
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()
    fun startMapActivity(client: TodayTripResponse.Data.Down.Client)=viewInteractor?.startMapActivity(client)
}