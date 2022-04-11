package com.hamid.template.ui.facilitiesPatiensts

import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList


interface FacilityContracts : ViewInteractor {
    fun setData()
    fun showPatientsList()
    fun onButtonBackPressed()
    fun startMapActivity(client: TodayTripResponse.Data.Down.Client)
    fun ShowLoading()
    fun HideLoading()
    fun onCheckListClicked()
    fun getFormsList(clicked:Boolean, client: TodayTripResponse.Data.Down.Client)
    fun showSelectFormDialog(documentList: ResponseDocumentList, client: TodayTripResponse.Data.Down.Client)
    fun apiCallForUrl(form: ResponseDocumentList.DataItem, client: TodayTripResponse.Data.Down.Client)
    fun moveToTodayTrips(group: TodayTripResponse.Data.Down)
}