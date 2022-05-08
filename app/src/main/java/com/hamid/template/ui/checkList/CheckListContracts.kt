package com.hamid.template.ui.checkList

import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse


interface CheckListContracts : ViewInteractor {
    fun setData()
    fun onButtonBackPressed()
    fun ShowLoading()
    fun HideLoading()
    fun checkForCheckList()

    fun apiCallForUrl(checK: UserCheckListResponse.Data.CheckListItem, client: TodayTripResponse.Data.Down.Client)
    fun checkListCompleted()
    fun showMedicationDialog(checK: UserCheckListResponse.Data.CheckListItem)
}