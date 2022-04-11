package com.hamid.template.ui.missingForms.eventListners

import com.hamid.template.ui.missingForms.model.ResponseUserMissingDocument

interface MissingFormClicked {
    fun openClicked(item: ResponseUserMissingDocument.DataItem)
    fun editClicked(item: ResponseUserMissingDocument.DataItem)
    fun deleteClicked(item: ResponseUserMissingDocument.DataItem)
}