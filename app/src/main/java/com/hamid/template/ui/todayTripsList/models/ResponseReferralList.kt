package com.hamid.template.ui.todayTripsList.models

data class ResponseReferralList(
	val isSuccess: Boolean,
	val errors: Any,
	val message: Any,
	val data: List<DataItem>,
	val code: String
){
	data class DataItem(
		val isTripComplete: Boolean,
		val referralName: String,
		val transportationGroupID: Int,
		val referralID: Int,
		val scheduleID: Int
	)
}



