package com.hamid.template.ui.fillForm

import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse


interface FillFormContracts : ViewInteractor {
    fun setData()
    fun onButtonBackPressed()
    fun ShowLoading()
    fun HideLoading()
    fun saveAndFinish(html:String)
    fun checkListSave(checK: UserCheckListResponse.Data.CheckListItem,html: String)
}