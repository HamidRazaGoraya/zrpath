package com.hamid.template.ui.todayTripsList.models

import com.google.gson.annotations.SerializedName

data class RequestDashboardAPI(

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


