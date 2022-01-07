package com.hamid.template.ui.loginAndRegister.logInRequestModel


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class LogInRequest(
    @SerializedName("Data")
    @Expose
    val `data`: Data,
    @SerializedName("Key")
    @Expose
    val key: String, // string
    @SerializedName("Token")
    @Expose
    val token: String // string
) {
    data class Data(
        @SerializedName("DeviceOSVersion")
        @Expose
        val deviceOSVersion: String, // string
        @SerializedName("DeviceType")
        @Expose
        val deviceType: String, // string
        @SerializedName("DeviceUDID")
        @Expose
        val deviceUDID: String, // string
        @SerializedName("IsLoginViaPassword")
        @Expose
        val isLoginViaPassword: Boolean, // true
        @SerializedName("Password")
        @Expose
        val password: String, // string
        @SerializedName("UserName")
        @Expose
        val userName: String // string
    )
}