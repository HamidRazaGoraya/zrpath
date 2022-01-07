package com.hamid.template.ui.loginAndRegister.logResponseModel


import com.google.gson.annotations.SerializedName

data class LogInResponse(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Data")
    val `data`: Data,
    @SerializedName("Errors")
    val errors: List<Error>,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean,
    @SerializedName("Message")
    val message: String
)