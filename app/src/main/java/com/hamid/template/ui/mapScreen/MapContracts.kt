package com.hamid.template.ui.mapScreen

import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList


interface MapContracts : ViewInteractor {
    fun setData()
    fun onButtonBackPressed()
    fun ShowLoading()
    fun HideLoading()
    fun fillUserDetails(client: TodayTripResponse.Data.Down.Client)
    fun checkForCheckList()
    fun onCheckListClicked()
    fun getFormsList(clicked:Boolean)
    fun showSelectFormDialog(documentList: ResponseDocumentList)
    fun apiCallForUrl(form:ResponseDocumentList.DataItem)
}