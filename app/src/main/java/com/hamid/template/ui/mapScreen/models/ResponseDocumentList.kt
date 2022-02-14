package com.hamid.template.ui.mapScreen.models

import com.google.gson.annotations.SerializedName

data class ResponseDocumentList(

    @field:SerializedName("IsSuccess")
	val isSuccess: Boolean,

    @field:SerializedName("Errors")
	val errors: List<ErrorsItem>,

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

		@field:SerializedName("Version")
		val version: String,

		@field:SerializedName("IsActive")
		val isActive: Boolean,

		@field:SerializedName("NameForUrl")
		val nameForUrl: String,

		@field:SerializedName("IsOrbeonForm")
		val isOrbeonForm: Boolean,

		@field:SerializedName("Count")
		val count: Int,

		@field:SerializedName("EBFormID")
		val eBFormID: String,

		@field:SerializedName("FormLongName")
		val formLongName: String,

		@field:SerializedName("OrganizationOrbeonformID")
		val organizationOrbeonformID: Int,

		@field:SerializedName("Name")
		val name: String
	)
}



data class ErrorsItem(

	@field:SerializedName("Field")
	val field: String? = null,

	@field:SerializedName("Message")
	val message: String? = null
)
