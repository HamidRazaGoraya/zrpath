package com.hamid.template.ui.facilitiesPatiensts.models

import com.google.gson.annotations.SerializedName

data class RequestTransportDetails(

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
		val employeeID: Int
	)
}
