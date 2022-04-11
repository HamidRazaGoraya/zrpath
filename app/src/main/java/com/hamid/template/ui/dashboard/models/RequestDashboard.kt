package com.hamid.template.ui.dashboard.models

import com.google.gson.annotations.SerializedName

data class RequestDashboard(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Key")
	val key: String
)
