package com.hamid.template.ui.missingForms.model

import com.google.gson.annotations.SerializedName

data class ResponseUserMissingDocument(

	@field:SerializedName("IsSuccess")
	val isSuccess: Boolean,

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

		@field:SerializedName("IsDeleted")
		val isDeleted: Boolean,

		@field:SerializedName("IsActive")
		val isActive: Boolean,

		@field:SerializedName("DocumentType")
		val documentType: String,

		@field:SerializedName("MissingDocumentName")
		val missingDocumentName: String,

		@field:SerializedName("Count")
		val count: Int,

		@field:SerializedName("EBFormID")
		val eBFormID: String?,

		@field:SerializedName("FormLongName")
		val formLongName: String? = null,

		@field:SerializedName("Name")
		val name: String? = null,

		@field:SerializedName("MissingDocumentType")
		val missingDocumentType: String? = null,

		@field:SerializedName("Version")
		val version: Int? = null,

		@field:SerializedName("NameForUrl")
		val nameForUrl: String? = null,

		@field:SerializedName("IsOrbeonForm")
		val isOrbeonForm: Boolean,

		@field:SerializedName("Row")
		val row: Int,

		@field:SerializedName("OrganizationOrbeonformID")
		val organizationOrbeonformID: Any? = null
	)
}


