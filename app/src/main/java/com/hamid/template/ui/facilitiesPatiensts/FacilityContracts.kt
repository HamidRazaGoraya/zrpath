package com.hamid.template.ui.facilitiesPatiensts

import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse


interface FacilityContracts : ViewInteractor {
    fun setData()
    fun showPatientsList()
    fun onButtonBackPressed()
    fun startMapActivity(client: TodayTripResponse.Data.Down.Client)
    fun ShowLoading()
    fun HideLoading()
}