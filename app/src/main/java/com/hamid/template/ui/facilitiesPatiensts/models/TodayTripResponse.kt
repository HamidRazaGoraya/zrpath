package com.hamid.template.ui.facilitiesPatiensts.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.hamid.template.ui.mapScreen.models.ResponseTripDetails

data class TodayTripResponse(
    @SerializedName("Code")
    @Expose
    val code: String, // 200
    @SerializedName("Data")
    @Expose
    val `data`: Data,
    @SerializedName("Errors")
    @Expose
    val errors: Any?, // null
    @SerializedName("IsSuccess")
    @Expose
    val isSuccess: Boolean, // true
    @SerializedName("Message")
    @Expose
    val message: String // null
) {
    data class Data(
        @SerializedName("DownList")
        @Expose
        val downList: List<Down>,
        @SerializedName("UpList")
        @Expose
        val upList: List<Down>
    ) {
        data class Down(
            @SerializedName("ClientList")
            @Expose
            val clientList: List<Client>,
            @SerializedName("TransportationGroup")
            @Expose
            val transportationGroup: TransportationGroup,
            @SerializedName("TransportationGroupID")
            @Expose
            val transportationGroupID: Int, // null
            @Expose
            var TripState:Int=0,
            @Expose
            var responseTripDetails: ResponseTripDetails?=null
        ) {
            data class Client(
                @SerializedName("scheduleID")
                @Expose
                val scheduleID:Int,
                @SerializedName("GroupTripStatus")
                @Expose
                val GroupTripStatus:String,
                @SerializedName("ChildTripStatus")
                @Expose
                val ChildTripStatus:String,
                @SerializedName("IsCheckListCompleted")
                @Expose
                val IsCheckListCompleted:Boolean,
                @SerializedName("PatientSignature")
                val PatientSignature:String?,

                @SerializedName("ReferralID")
                @Expose
                val ReferralID:Int,
                @SerializedName("Address")
                @Expose
                val address: String, // 4231 East Santa Fe Lane
                @SerializedName("Age")
                @Expose
                val age: String, // 6.10
                @SerializedName("Capacity")
                @Expose
                val capacity: Int, // 10
                @SerializedName("City")
                @Expose
                val city: String, // Gilbert
                @SerializedName("FacilityColorScheme")
                @Expose
                val facilityColorScheme: String, // #dee81e
                @SerializedName("FacilityID")
                @Expose
                val facilityID: Int, // 1
                @SerializedName("FacilityName")
                @Expose
                val facilityName: String, // PHX Apache
                @SerializedName("FullAddress")
                @Expose
                val fullAddress: String, // 4231 East Santa Fe Lane, Gilbert, AZ - 85297
                @SerializedName("Gender")
                @Expose
                val gender: String, // M
                @SerializedName("GroupFacilityName")
                @Expose
                val groupFacilityName: String, // PHX Apache
                @SerializedName("GroupLocation")
                @Expose
                val groupLocation: String, // Client Home (Phoenix)
                @SerializedName("GroupName")
                @Expose
                val groupName: String, // FacilityHamid
                @SerializedName("IsDeleted")
                @Expose
                val isDeleted: Boolean, // false
                @SerializedName("IsReferralDeleted")
                @Expose
                val isReferralDeleted: Boolean, // false
                @SerializedName("Location")
                @Expose
                val location: String, // Gilbert (Weekend) - Sign In/Out
                @SerializedName("LocationID")
                @Expose
                val locationID: Int, // 3
                @SerializedName("Name")
                @Expose
                val name: String, // Adams, Aaron
                @SerializedName("ParentName")
                @Expose
                val parentName: String, // Adams, Carissa and Sean
                @SerializedName("Phone")
                @Expose
                val phone: String, // 7274703117
                @SerializedName("ScheduleStatusName")
                @Expose
                val scheduleStatusName: String, // Unconfirmed
                @SerializedName("StaffIDs")
                @Expose
                val staffIDs: String, // 10495
                @SerializedName("StaffNames")
                @Expose
                val staffNames: String, //  Raza, Hamid
                @SerializedName("State")
                @Expose
                val state: String, // AZ
                @SerializedName("TransportationDate")
                @Expose
                val transportationDate: String, // 2022-01-19T00:00:00 -08:00
                @SerializedName("TransportationFilterID")
                @Expose
                val transportationFilterID: List<Int>,
                @SerializedName("TransportationFilterIDs")
                @Expose
                val transportationFilterIDs: String?, // 1
                @SerializedName("TransportationFilterName")
                @Expose
                val transportationFilterName: List<String>,
                @SerializedName("TransportationFilterNames")
                @Expose
                val transportationFilterNames: String?, // Booster Seat
                @SerializedName("TransportationGroupClientID")
                @Expose
                val transportationGroupClientID: Int, // 47443
                @SerializedName("TransportationGroupID")
                @Expose
                val transportationGroupID: Int, // 7583
                @SerializedName("TransportationUpGroupID")
                @Expose
                val transportationUpGroupID: Int, // 7581
                @SerializedName("TripDirection")
                @Expose
                val tripDirection: String, // DOWN
                @SerializedName("ZipCode")
                @Expose
                val zipCode: String // 85297
            )
            data class TransportationGroup(
                @SerializedName("GroupTripStatus")
                @Expose
                val GroupTripStatus:String,
                @SerializedName("ReferralID")
                @Expose
                val ReferralID:Int,
                @SerializedName("Address")
                @Expose
                val address: String, // 4231 East Santa Fe Lane
                @SerializedName("Age")
                @Expose
                val age: String, // 8.8
                @SerializedName("Capacity")
                @Expose
                val capacity: Int, // 10
                @SerializedName("City")
                @Expose
                val city: String, // Gilbert
                @SerializedName("FacilityColorScheme")
                @Expose
                val facilityColorScheme: String?, // #dee81e
                @SerializedName("FacilityID")
                @Expose
                val facilityID: Int, // 1
                @SerializedName("FacilityName")
                @Expose
                val facilityName: String, // PHX Apache
                @SerializedName("FullAddress")
                @Expose
                val fullAddress: String, // 4231 East Santa Fe Lane, Gilbert, AZ - 85297
                @SerializedName("Gender")
                @Expose
                val gender: String, // F
                @SerializedName("GroupFacilityName")
                @Expose
                val groupFacilityName: String, // PHX Apache
                @SerializedName("GroupLocation")
                @Expose
                val groupLocation: String, // Client Home (Phoenix)
                @SerializedName("GroupName")
                @Expose
                val groupName: String, // FacilityHamid
                @SerializedName("IsDeleted")
                @Expose
                val isDeleted: Boolean, // false
                @SerializedName("IsReferralDeleted")
                @Expose
                val isReferralDeleted: Boolean, // false
                @SerializedName("Location")
                @Expose
                val location: String, // Gilbert (Weekend) - Sign In/Out
                @SerializedName("LocationID")
                @Expose
                val locationID: Int, // 3
                @SerializedName("Name")
                @Expose
                val name: String, // Adams, Ashleigh
                @SerializedName("ParentName")
                @Expose
                val parentName: String, // Adams, Carissa and Sean
                @SerializedName("Phone")
                @Expose
                val phone: String, // 7274703117
                @SerializedName("ScheduleStatusName")
                @Expose
                val scheduleStatusName: String, // Unconfirmed
                @SerializedName("StaffIDs")
                @Expose
                val staffIDs: String, // 10495
                @SerializedName("StaffNames")
                @Expose
                val staffNames: String, //  Raza, Hamid
                @SerializedName("State")
                @Expose
                val state: String, // AZ
                @SerializedName("TransportationDate")
                @Expose
                val transportationDate: String, // 2022-01-19T00:00:00 -08:00
                @SerializedName("TransportationFilterID")
                @Expose
                val transportationFilterID: List<Any>,
                @SerializedName("TransportationFilterIDs")
                @Expose
                val transportationFilterIDs: Any?, // null
                @SerializedName("TransportationFilterName")
                @Expose
                val transportationFilterName: List<Any>,
                @SerializedName("TransportationFilterNames")
                @Expose
                val transportationFilterNames: Any?, // null
                @SerializedName("TransportationGroupClientID")
                @Expose
                val transportationGroupClientID: Int, // 47445
                @SerializedName("TransportationGroupID")
                @Expose
                val transportationGroupID: Int, // 7583
                @SerializedName("TransportationUpGroupID")
                @Expose
                val transportationUpGroupID: Int, // 7581
                @SerializedName("TripDirection")
                @Expose
                val tripDirection: String, // DOWN
                @SerializedName("ZipCode")
                @Expose
                val zipCode: String // 85297
            )
        }
    }
}