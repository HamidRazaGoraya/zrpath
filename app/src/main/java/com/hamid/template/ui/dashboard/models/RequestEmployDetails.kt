package com.hamid.template.ui.dashboard.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class RequestEmployDetails(
    @SerializedName("Data")
    @Expose
    val `data`: Data,
    @SerializedName("Key")
    @Expose
    val key: String, // A_657c48d0-915a-4fdc-a3e4-90b1fef38344
    @SerializedName("Token")
    @Expose
    val token: String // 30d63b8f-6a4b-4b36-b486-3ec0efa4c2c9
) {
    data class Data(
        @SerializedName("EmployeeID")
        @Expose
        val employeeID: Int // 10493
    )
}