package com.hamid.template.ui.medicationFormsList

import com.hamid.template.base.BaseViewModel
import com.hamid.template.network.ApiRepository
import com.hamid.template.ui.checkList.models.RequestMedicationFormsList
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.fillForm.model.RequestSavedOpenForm
import com.hamid.template.ui.mapScreen.models.*
import com.hamid.template.ui.medicationFormsList.model.ResponseMedicationFormsList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicaationFormsVM @Inject
constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<MedicationFormsContracts>() {

    fun initThings(client:TodayTripResponse.Data.Down.Client) {
        viewInteractor?.setData()
        viewInteractor?.setUpFormsList()
    }
    lateinit var client:TodayTripResponse.Data.Down.Client
    lateinit var visitdetails: ResponseTripDetails

    fun onButtonBackPressed()=viewInteractor?.onButtonBackPressed()
    fun ShowLoading()=viewInteractor?.ShowLoading()
    fun HideLoading()=viewInteractor?.HideLoading()
    fun getMedicationFormsList()=viewInteractor?.getMedicationFormsList()
    fun loadApiCallMedicationForms(data: RequestMedicationFormsList.Data)=apiRepository.GetMedicationFormList(data)
    fun moveToOpenFormActivity(item: ResponseMedicationFormsList.DataItem)=viewInteractor?.moveToOpenFormActivity(item)
    fun openSavedForm(data: RequestSavedOpenForm.Data)=apiRepository.openSavedForm(data)
}