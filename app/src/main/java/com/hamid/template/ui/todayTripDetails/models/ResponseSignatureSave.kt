package com.hamid.template.ui.todayTripDetails.models

import com.google.gson.annotations.SerializedName

data class ResponseSignatureSave(

	@field:SerializedName("IsSuccess")
	val isSuccess: Boolean? = null,

	@field:SerializedName("Errors")
	val errors: Any? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Data")
	val data: Any? = null,

	@field:SerializedName("Code")
	val code: String? = null
)
