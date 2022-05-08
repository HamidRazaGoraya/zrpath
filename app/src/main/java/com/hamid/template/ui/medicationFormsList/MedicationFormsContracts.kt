package com.hamid.template.ui.medicationFormsList

import com.hamid.template.base.ViewInteractor
import com.hamid.template.ui.medicationFormsList.model.ResponseMedicationFormsList


interface MedicationFormsContracts : ViewInteractor {
    fun setData()
    fun onButtonBackPressed()
    fun ShowLoading()
    fun HideLoading()
    fun setUpFormsList()
    fun getMedicationFormsList()
    fun moveToOpenFormActivity(item: ResponseMedicationFormsList.DataItem)
}