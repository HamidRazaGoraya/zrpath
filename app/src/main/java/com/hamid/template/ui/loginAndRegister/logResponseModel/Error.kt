package com.hamid.template.ui.loginAndRegister.logResponseModel


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("Field")
    val `field`: String,
    @SerializedName("Message")
    val message: String
)