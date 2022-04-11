package com.hamid.template.ui.missingForms.model

import com.google.gson.annotations.SerializedName

data class RequestMissingDocument(

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

		@field:SerializedName("EmployeeID")
		val employeeID: Int
	)
}
