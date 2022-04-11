package com.hamid.template.ui.loginAndRegister.model

import com.google.gson.annotations.SerializedName

data class Savepassowrd(

	@field:SerializedName("saved")
	val saved: Boolean,

	@field:SerializedName("listOfPasswords")
	val listOfPasswords: List<Passwords>
){
	data class Passwords(
		@field:SerializedName("password")
		val password: String,
		@field:SerializedName("email")
		val email: String,
	)
}
