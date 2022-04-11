package com.hamid.template.ui.missingForms.model

import com.google.gson.annotations.SerializedName

data class ResponseMissingDocument(

	@field:SerializedName("IsSuccess")
	val isSuccess: Boolean? = null,

	@field:SerializedName("Errors")
	val errors: Any? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Data")
	val data: List<DataItem>? = null,

	@field:SerializedName("Code")
	val code: String? = null
){
	data class DataItem(

		@field:SerializedName("FilePath")
		val filePath: String? = null,

		@field:SerializedName("KindOfDocument")
		val kindOfDocument: String? = null,

		@field:SerializedName("CreatedBy")
		val createdBy: Any? = null,

		@field:SerializedName("ReferralDocumentID")
		val referralDocumentID: Int,

		@field:SerializedName("FileName")
		val fileName: String? = null,

		@field:SerializedName("Count")
		val count: Int? = null,

		@field:SerializedName("StoreType")
		val storeType: Any? = null,

		@field:SerializedName("Version")
		val version: Any? = null,

		@field:SerializedName("ReferralID")
		val referralID: Int,

		@field:SerializedName("CreatedDate")
		val createdDate: String? = null,

		@field:SerializedName("NameForUrl")
		val nameForUrl: Any? = null,

		@field:SerializedName("Row")
		val row: Int? = null,

		@field:SerializedName("OrbeonFormID")
		val orbeonFormID: String? = null
	)
}


