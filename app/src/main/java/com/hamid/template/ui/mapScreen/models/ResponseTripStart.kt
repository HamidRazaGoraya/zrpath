package com.hamid.template.ui.mapScreen.models

import com.google.gson.annotations.SerializedName

data class ResponseTripStart(

	@field:SerializedName("IsSuccess")
	val isSuccess: Boolean,

	@field:SerializedName("Errors")
	val errors: Any,

	@field:SerializedName("Message")
	val message: String,

	@field:SerializedName("Data")
	val data: Int,

	@field:SerializedName("Code")
	val code: String
)
