package com.hamid.template.ui.medicationFormsList.events

import com.hamid.template.ui.medicationFormsList.model.ResponseMedicationFormsList

interface MedicationFormClicked {
    fun openClicked(item: ResponseMedicationFormsList.DataItem)
    fun editClicked(item: ResponseMedicationFormsList.DataItem)
    fun deleteClicked(item: ResponseMedicationFormsList.DataItem)
}