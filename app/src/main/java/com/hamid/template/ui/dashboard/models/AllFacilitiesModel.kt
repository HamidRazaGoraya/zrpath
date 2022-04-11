package com.hamid.template.ui.dashboard.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class AllFacilitiesModel(
    @SerializedName("Code")
    @Expose
    val code: String, // 200
    @SerializedName("Data")
    @Expose
    val `data`: List<Data>,
    @SerializedName("Errors")
    @Expose
    val errors: Any?, // null
    @SerializedName("IsSuccess")
    @Expose
    val isSuccess: Boolean, // true
    @SerializedName("Message")
    @Expose
    val message: Any? // null
) {
    data class Data(
        @SerializedName("FacilityID")
        @Expose
        val facilityID: Int, // 1
        @SerializedName("FacilityName")
        @Expose
        val facilityName: String, // PHX Apache

    )
}