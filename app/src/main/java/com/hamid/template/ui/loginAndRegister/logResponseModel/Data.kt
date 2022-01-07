package com.hamid.template.ui.loginAndRegister.logResponseModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Employee")
    val employee: Employee,
    @SerializedName("LoginResponse")
    val loginResponse: LoginResponseX,
    @SerializedName("Permissions")
    val permissions: List<Permission>,
    @SerializedName("ShowCaptcha")
    val showCaptcha: Boolean
)