package com.hamid.template.utils.dialogs.eventsListners

import com.hamid.template.ui.dashboard.models.ResponseDashBoard
import com.hamid.template.ui.dashboard.models.VisitListModel
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.mapScreen.models.ResponseDocumentList
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList


interface AllFormsListners {
    fun onFormClicked(item: ResponseDocumentList.DataItem)
}
interface OnRefferalClicked{
    fun onClicked(visitListModel: VisitListModel)
    fun onPrepareClicked(visitListModel: VisitListModel)
    fun onStartTripClicked(visitListModel: VisitListModel)
    fun onEndTripClicked(visitListModel: VisitListModel)
    fun onGroupTripStart(group: TodayTripResponse.Data.Down.TransportationGroup)
    fun onGroupTripEnd(group: TodayTripResponse.Data.Down.TransportationGroup)
    fun onDropOfSignatureClicked(visitListModel: VisitListModel)
}