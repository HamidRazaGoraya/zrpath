package com.hamid.template.ui.loginAndRegister.models

import com.google.gson.annotations.SerializedName

data class LogInRequest(

	@field:SerializedName("IsRemember")
	val isRemember: Boolean,

	@field:SerializedName("IsPin")
	val isPin: Boolean,

	@field:SerializedName("UserName")
	val userName: String,

	@field:SerializedName("Password")
	val password: String
)
