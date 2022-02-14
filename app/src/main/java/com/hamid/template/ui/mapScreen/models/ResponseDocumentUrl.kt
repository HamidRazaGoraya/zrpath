package com.hamid.template.ui.mapScreen.models

import com.google.gson.annotations.SerializedName

data class ResponseDocumentUrl(

	@field:SerializedName("IsSuccess")
	val isSuccess: Boolean,

	@field:SerializedName("Errors")
	val errors: List<ErrorsItem>,

	@field:SerializedName("Message")
	val message: String,

	@field:SerializedName("Data")
	val data: String,

	@field:SerializedName("Code")
	val code: String
){
	data class ErrorsItem(

		@field:SerializedName("Field")
		val field: String,

		@field:SerializedName("Message")
		val message: String
	)
}


