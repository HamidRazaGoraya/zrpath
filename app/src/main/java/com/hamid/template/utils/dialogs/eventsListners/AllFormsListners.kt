package com.hamid.template.utils.dialogs.eventsListners

import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList


interface AllFormsListners {
    fun onFormClicked(item: ResponseDocumentList.DataItem)
}
interface OnRefferalClicked{
    fun onClicked(item: ResponseDashBoard.Data.VisitItem)
}