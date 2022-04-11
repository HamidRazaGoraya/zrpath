package com.hamid.template.ui.checkList.models

import com.google.gson.annotations.SerializedName

data class RequestSelfCheckList(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Key")
	val key: String
){
	data class Data(

		@field:SerializedName("TransportVisitID")
		val transportVisitID: Int,

		@field:SerializedName("ScheduleID")
		val scheduleID: Int
	)
}


