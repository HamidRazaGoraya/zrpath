package com.hamid.template.ui.fillForm.model

import com.google.gson.annotations.SerializedName

data class RequestSaveForm(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Key")
	val key: String
){
	data class Data(

		@field:SerializedName("IsEmployeeDocument")
		val isEmployeeDocument: Boolean,

		@field:SerializedName("ReferralDocumentID")
		val referralDocumentID: Int,

		@field:SerializedName("ReferralID")
		val referralID: Int,

		@field:SerializedName("ComplianceID")
		val complianceID: Int,

		@field:SerializedName("EmployeeID")
		val employeeID: Int,

		@field:SerializedName("OrbeonFormID")
		val orbeonFormID: String
	)
}
