package com.hamid.template.ui.fillForm.model

import com.google.gson.annotations.SerializedName

data class ResponseSaveForm(

	@field:SerializedName("IsSuccess")
	val isSuccess: Boolean? = null,

	@field:SerializedName("Errors")
	val errors: Any? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Data")
	val data: Data? = null,

	@field:SerializedName("Code")
	val code: String? = null
){
	data class Data(

		@field:SerializedName("FilePath")
		val filePath: String? = null,

		@field:SerializedName("KindOfDocument")
		val kindOfDocument: String? = null,

		@field:SerializedName("CreatedBy")
		val createdBy: Int? = null,

		@field:SerializedName("UpdatedDate")
		val updatedDate: String? = null,

		@field:SerializedName("ReferralDocumentID")
		val referralDocumentID: Int? = null,

		@field:SerializedName("FileName")
		val fileName: String? = null,

		@field:SerializedName("SystemID")
		val systemID: String? = null,

		@field:SerializedName("DocumentTypeID")
		val documentTypeID: Int? = null,

		@field:SerializedName("UpdatedBy")
		val updatedBy: Int? = null,

		@field:SerializedName("ExpirationDate")
		val expirationDate: Any? = null,

		@field:SerializedName("UserID")
		val userID: Int? = null,

		@field:SerializedName("CreatedDate")
		val createdDate: String? = null,

		@field:SerializedName("ComplianceID")
		val complianceID: Int? = null,

		@field:SerializedName("UserType")
		val userType: Int? = null
	)
}


