package com.hamid.template.ui.fillForm.model

import com.google.gson.annotations.SerializedName

data class RequestDeleteDocument(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Key")
	val key: String
){
	data class Data(

		@field:SerializedName("ReferralDocumentID")
		val referralDocumentID: Int,

		@field:SerializedName("EmployeeID")
		val employeeID: Int
	)
}


