package com.hamid.template.ui.facilitiesPatiensts.models

import com.google.gson.annotations.SerializedName

data class RequestSetTime(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Key")
	val key: String
){
	data class Data(


		@field:SerializedName("Type")
		val type: Int,

		@field:SerializedName("PickUpLongitude")
		val pickUpLongitude: Double?,

		@field:SerializedName("TransportVisitID")
		val transportVisitID: Int,

		@field:SerializedName("TransportationGroupID")
		val transportationGroupID: Int,

		@field:SerializedName("PickUpLatitude")
		val pickUpLatitude: Double?,

		@field:SerializedName("DropOffLongitude")
		val dropOffLongitude: Double?,

		@field:SerializedName("DropOffLatitude")
		val dropOffLatitude: Double?,

		@field:SerializedName("ScheduleID")
		val scheduleID: Int,

		@field:SerializedName("EmployeeID")
		val employeeID: Int
	)
}


