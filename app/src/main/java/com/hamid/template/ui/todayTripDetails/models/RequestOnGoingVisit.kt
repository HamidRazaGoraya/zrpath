package com.hamid.template.ui.todayTripDetails.models

import com.google.gson.annotations.SerializedName

data class RequestOnGoingVisit(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Key")
	val key: String
){
	data class Data(

		@field:SerializedName("ScheduleID")
		val scheduleID: Int,

		@field:SerializedName("EmployeeID")
		val employeeID: Int,
		@field:SerializedName("ReferralID")
		val ReferralID:Int
	)
}


