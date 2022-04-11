package com.hamid.template.ui.missingForms.eventListners

import com.hamid.template.ui.missingForms.model.ResponseMissingDocument

interface FormClicked {
    fun openClicked(item: ResponseMissingDocument.DataItem)
    fun editClicked(item: ResponseMissingDocument.DataItem)
    fun deleteClicked(item: ResponseMissingDocument.DataItem)
}