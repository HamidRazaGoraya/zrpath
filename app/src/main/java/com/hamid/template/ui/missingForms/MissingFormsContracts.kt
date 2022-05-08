package com.hamid.template.ui.missingForms

import android.os.Bundle
import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.model.ResponseSaveForm
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.mapScreen.models.ResponseDocumentUrl
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse
import com.hamid.template.ui.missingForms.model.ResponseMissingDocument


interface MissingFormsContracts : ViewInteractor {
    fun setData()
    fun onButtonBackPressed()
    fun ShowLoading()
    fun HideLoading()
    fun getFormsList(clicked:Boolean, client: TodayTripResponse.Data.Down.Client)
    fun showSelectFormDialog(documentList: ResponseDocumentList, client: TodayTripResponse.Data.Down.Client)
    fun apiCallForUrl(form: String, client: TodayTripResponse.Data.Down.Client)
    fun openClicked(item: ResponseMissingDocument.DataItem)
    fun editClicked(item: ResponseMissingDocument.DataItem)
    fun deleteClicked(item: ResponseMissingDocument.DataItem)
    fun setUpTabLayout()
    fun openNewFormActivity(data:ResponseDocumentUrl, client: TodayTripResponse.Data.Down.Client)
    fun openSavedFormActivity(bundle: Bundle)
}