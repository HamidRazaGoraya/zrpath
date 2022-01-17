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
        @SerializedName("AHCCCSID")
        @Expose
        val aHCCCSID: Any?, // null
        @SerializedName("Address")
        @Expose
        val address: Any?, // null
        @SerializedName("BadCapacity")
        @Expose
        val badCapacity: Any?, // null
        @SerializedName("Cell")
        @Expose
        val cell: Any?, // null
        @SerializedName("City")
        @Expose
        val city: Any?, // null
        @SerializedName("Count")
        @Expose
        val count: Int, // 0
        @SerializedName("County")
        @Expose
        val county: Any?, // null
        @SerializedName("DefaultScheduleStatusID")
        @Expose
        val defaultScheduleStatusID: Any?, // null
        @SerializedName("EIN")
        @Expose
        val eIN: Any?, // null
        @SerializedName("EncryptedFacilityID")
        @Expose
        val encryptedFacilityID: String, // iSNqtcWbe3gZEhtctmlPcA2
        @SerializedName("FacilityBillingName")
        @Expose
        val facilityBillingName: Any?, // null
        @SerializedName("FacilityColorScheme")
        @Expose
        val facilityColorScheme: Any?, // null
        @SerializedName("FacilityID")
        @Expose
        val facilityID: Int, // 1
        @SerializedName("FacilityName")
        @Expose
        val facilityName: String, // PHX Apache
        @SerializedName("FirePermitDate")
        @Expose
        val firePermitDate: Any?, // null
        @SerializedName("GSA")
        @Expose
        val gSA: Any?, // null
        @SerializedName("IsDeleted")
        @Expose
        val isDeleted: Boolean, // false
        @SerializedName("Licensure")
        @Expose
        val licensure: Any?, // null
        @SerializedName("LicensureRenewalDate")
        @Expose
        val licensureRenewalDate: Any?, // null
        @SerializedName("NPI")
        @Expose
        val nPI: Any?, // null
        @SerializedName("ParentFacilityID")
        @Expose
        val parentFacilityID: Int, // 0
        @SerializedName("PayorApproved")
        @Expose
        val payorApproved: Any?, // null
        @SerializedName("Phone")
        @Expose
        val phone: Any?, // null
        @SerializedName("PrivateRoomCount")
        @Expose
        val privateRoomCount: Any?, // null
        @SerializedName("ProviderType")
        @Expose
        val providerType: Any?, // null
        @SerializedName("RegionID")
        @Expose
        val regionID: Int, // 0
        @SerializedName("RegionName")
        @Expose
        val regionName: Any?, // null
        @SerializedName("Row")
        @Expose
        val row: Int, // 0
        @SerializedName("SelectedPayors")
        @Expose
        val selectedPayors: Any?, // null
        @SerializedName("SetSelectedPayors")
        @Expose
        val setSelectedPayors: Any?, // null
        @SerializedName("SiteType")
        @Expose
        val siteType: Any?, // null
        @SerializedName("State")
        @Expose
        val state: Any?, // null
        @SerializedName("ZipCode")
        @Expose
        val zipCode: Any? // null
    )
}