package com.hamid.template.ui.facilitiesPatiensts.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class TodayTripRequest(
    @SerializedName("Data")
    @Expose
    val `data`: Data,
    @SerializedName("Key")
    @Expose
    val key: String, // A_657c48d0-915a-4fdc-a3e4-90b1fef38344
    @SerializedName("Token")
    @Expose
    val token: String // a467a875-ea91-4e93-ae19-8cfd5154e80
) {
    data class Data(
        @SerializedName("Date")
        @Expose
        val date:String,
        @SerializedName("EmployeeID")
        @Expose
        val employeeID: Int, // 10495
        @SerializedName("FacilityID")
        @Expose
        val facilityID: Int, // 1
        @SerializedName("TripDirection")
        @Expose
        val tripDirection: String // Down
    )
}