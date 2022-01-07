package com.hamid.template.ui.loginAndRegister.logResponseModel


import com.google.gson.annotations.SerializedName

data class CachedData(
    @SerializedName("DeviceUDID")
    val deviceUDID: String,
    @SerializedName("EmployeeId")
    val employeeId: Int,
    @SerializedName("ExpireLogin")
    val expireLogin: String,
    @SerializedName("Platform")
    val platform: String
)