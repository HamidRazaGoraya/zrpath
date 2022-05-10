package com.hamid.template.ui.mapScreen.models

import com.google.gson.annotations.SerializedName

data class RequestTripStart(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Key")
	val key: String
){
	data class Data(
		@field:SerializedName("TransportationGroupID")
		val transportationGroupID: Int,

		@field:SerializedName("EmployeeID")
		val employeeID: Int,

		@field:SerializedName("TripStartLatitude")
		val tripStartLatitude: Double,

		@field:SerializedName("TripStartLongitude")
		val tripStartLongitude: Double
	)
}


