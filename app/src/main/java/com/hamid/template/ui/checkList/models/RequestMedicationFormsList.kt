package com.hamid.template.ui.checkList.models

import com.google.gson.annotations.SerializedName

data class RequestMedicationFormsList(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Key")
	val key: String
){
	data class Data(

		@field:SerializedName("ReferralID")
		val referralID: Int,

		@field:SerializedName("EmployeeID")
		val employeeID: Int
	)
}


