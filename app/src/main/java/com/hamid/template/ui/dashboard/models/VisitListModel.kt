package com.hamid.template.ui.dashboard.models

import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse

class VisitListModel(var client: TodayTripResponse.Data.Down.Client?, var isHeading:Boolean, var group:TodayTripResponse.Data.Down.TransportationGroup)