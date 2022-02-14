package com.hamid.template.ui.mapScreen.models

import com.google.gson.annotations.SerializedName

data class RequestDocumentList(
	@field:SerializedName("Key")
	val key: String,
	@field:SerializedName("Token")
	val token: String

)
