package com.hamid.template.ui.dashboard

import com.hamid.template.base.ViewInteractor


interface MainContracts : ViewInteractor {
    fun initiate()
    fun ShowLoading()
    fun HideLoading()
    fun showSideMenu()
    fun hideSideMenu()
    fun logOutClicked()
    fun patientClicked()
    fun mapsClicked()
    fun settingsClicked()
    fun helpClicked()
    fun notificationClicked()
    fun searchClicked()
    fun onButtonBackPressed()
}