package com.hamid.template.utils.dialogs.eventsListners

import com.hamid.template.ui.mapScreen.models.ResponseDocumentList


interface AllFormsListners {
    fun onFormClicked(item: ResponseDocumentList.DataItem)
}