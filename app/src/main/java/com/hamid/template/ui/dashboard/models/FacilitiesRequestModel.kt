package com.hamid.template.ui.dashboard.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class FacilitiesRequestModel(
    @SerializedName("Data")
    @Expose
    val `data`: Data,
    @SerializedName("Key")
    @Expose
    val key: String, // A_657c48d0-915a-4fdc-a3e4-90b1fef38344
    @SerializedName("Token")
    @Expose
    val token: String // bb05234d-ee61-441f-83c8-0b48aa271693
) {
    data class Data(
        @SerializedName("EmployeeID")
        @Expose
        val employeeID: Int, // 10495
        @SerializedName("TripDirection")
        @Expose
        val tripDirection: String // UP
    )
}