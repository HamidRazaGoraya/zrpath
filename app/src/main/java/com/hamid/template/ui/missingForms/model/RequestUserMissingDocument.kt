package com.hamid.template.ui.missingForms.model

import com.google.gson.annotations.SerializedName

data class RequestUserMissingDocument(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Key")
	val key: String
){
	data class Data(

		@field:SerializedName("ReferralID")
		val referralID: Int
	)
}
