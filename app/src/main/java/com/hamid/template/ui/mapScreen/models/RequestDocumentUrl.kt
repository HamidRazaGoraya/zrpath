package com.hamid.template.ui.mapScreen.models

import com.google.gson.annotations.SerializedName

data class RequestDocumentUrl(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Key")
	val key: String
)
{
	data class Data(

		@field:SerializedName("SavePreference")
		val savePreference: Boolean,

		@field:SerializedName("ReferralID")
		val referralID: Int,

		@field:SerializedName("EBFormID")
		val eBFormID: String
	)
}

