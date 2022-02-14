package com.hamid.template.ui.mapScreen.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class UserCheckListRequest(
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
        @SerializedName("CurrentTime")
        @Expose
        val currentTime: String, // string
        @SerializedName("EmployeeID")
        @Expose
        val employeeID: Int, // 0
        @SerializedName("ReferralID")
        @Expose
        val referralID: Int, // 0
        @SerializedName("ScheduleID")
        @Expose
        val scheduleID: Int // 0
    )
}