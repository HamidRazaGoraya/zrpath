package com.hamid.template.ui.todayTripsList.models

data class RequestReferralList(
	val token: String,
	val data: Data,
	val key: String
){
	data class Data(
		val transportationGroupID: Int
	)
}

