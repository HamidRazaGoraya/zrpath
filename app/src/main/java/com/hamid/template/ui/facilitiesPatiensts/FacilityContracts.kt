package com.hamid.template.ui.facilitiesPatiensts

import com.hamid.template.base.ViewInteractor


interface FacilityContracts : ViewInteractor {
    fun setData()
    fun showPatientsList()
    fun onButtonBackPressed()
}