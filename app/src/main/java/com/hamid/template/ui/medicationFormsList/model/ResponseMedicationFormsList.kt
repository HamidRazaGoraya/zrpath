package com.hamid.template.ui.medicationFormsList.model

import com.google.gson.annotations.SerializedName

data class ResponseMedicationFormsList(

	@field:SerializedName("IsSuccess")
	val isSuccess: Boolean,

	@field:SerializedName("Errors")
	val errors: Any,

	@field:SerializedName("Message")
	val message: String,

	@field:SerializedName("Data")
	val data: List<DataItem>,

	@field:SerializedName("Code")
	val code: String
){
	data class DataItem(

		@field:SerializedName("FilePath")
		val filePath: String,

		@field:SerializedName("StoreType")
		val storeType: String,

		@field:SerializedName("KindOfDocument")
		val kindOfDocument: String,

		@field:SerializedName("CreatedBy")
		val createdBy: String,

		@field:SerializedName("Version")
		val version: Any,

		@field:SerializedName("ReferralDocumentID")
		val referralDocumentID: Int,

		@field:SerializedName("FileName")
		val fileName: String,

		@field:SerializedName("ReferralID")
		val referralID: Int,

		@field:SerializedName("CreatedDate")
		val createdDate: String,

		@field:SerializedName("NameForUrl")
		val nameForUrl: Any,

		@field:SerializedName("OrbeonFormID")
		val orbeonFormID: String
	)
}
