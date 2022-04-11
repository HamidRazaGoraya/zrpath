package com.hamid.template.ui.fillForm

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSaveCheck
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.model.RequestSaveForm
import com.hamid.template.ui.mapScreen.models.UserCheckListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FillFormVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<FillFormContracts>() {

    var isDataLoaded = false
    var isSaved=false
    fun initThings() {
        viewInteractor?.setData()
    }
    fun onButtonBackPressed()=viewInteractor?.onButtonBackPressed()
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()

    fun saveAndFinish(htlm:String)=viewInteractor?.saveAndFinish(htlm)
    fun requestSaveForm(data: RequestSaveForm.Data)=apiRepository.requestSaveForm(data)
    fun checkCheckList(data: RequestSaveCheck.Data)=apiRepository.getSaveCheckPoint(data)
    fun checkListSave(checK: UserCheckListResponse.Data.CheckListItem,html: String)=viewInteractor?.checkListSave(checK,html)

}