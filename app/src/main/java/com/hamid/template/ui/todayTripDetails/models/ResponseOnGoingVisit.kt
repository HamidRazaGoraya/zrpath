package com.hamid.template.ui.todayTripDetails.models

import com.google.gson.annotations.SerializedName

data class ResponseOnGoingVisit(

	@field:SerializedName("IsSuccess")
	val isSuccess: Boolean,

	@field:SerializedName("Errors")
	val errors: Any,

	@field:SerializedName("Message")
	val message: String,

	@field:SerializedName("Data")
	val data: Data?,

	@field:SerializedName("Code")
	val code: String
){
	data class Data(

		@field:SerializedName("ReferralDetail")
		val referralDetail: ReferralDetail?,

		@field:SerializedName("OnGoingVisitDetail")
		val onGoingVisitDetail: OnGoingVisitDetail?
	){
		data class ReferralDetail(

			@field:SerializedName("PatientAddress")
			val patientAddress: String,

			@field:SerializedName("Phone")
			val phone: String,

			@field:SerializedName("ReferralID")
			val referralID: Int,

			@field:SerializedName("Latitude")
			val latitude: Double,
			@field:SerializedName("ParentName")
			val ParentName: String,
			@field:SerializedName("PatientName")
			val patientName: String,

			@field:SerializedName("Longitude")
			val longitude: Double
		)

		data class OnGoingVisitDetail(
			@field:SerializedName("IsCheckListCompleted")
			val isCheckListCompleted:Boolean,
			@field:SerializedName("ScheduleID")
			val scheduleID:Int,

			@field:SerializedName("GroupName")
			val groupName: String,


			@field:SerializedName("TransportVisitID")
			val transportVisitID: Int?,

			@field:SerializedName("TransportationGroupID")
			val transportationGroupID: Int,


			@field:SerializedName("TransportationFilterNames")
			val transportationFilterNames: String,

			@field:SerializedName("PickUpTime")
			val pickUpTime: String?,

			@field:SerializedName("UpCheckListCount")
			val upCheckListCount: Int,

			@field:SerializedName("TransportationFilterIDs")
			val transportationFilterIDs: String,

			@field:SerializedName("AddedUpCheckListCount")
			val AddedUpCheckListCount: Int,

			@field:SerializedName("ScheduleStatusName")
			val scheduleStatusName: String,

			@field:SerializedName("DropOffTime")
			val dropOffTime: String?,

			@field:SerializedName("IsSigned")
		    val Signed: Boolean,
			@field:SerializedName("PatientSignature")
			val PatientSignature:String?
		)
	}
}




