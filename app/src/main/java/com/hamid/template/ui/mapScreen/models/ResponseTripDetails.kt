package com.hamid.template.ui.mapScreen.models

import com.google.gson.annotations.SerializedName

data class ResponseTripDetails(

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

		@field:SerializedName("IsDeleted")
		val isDeleted: Boolean,

		@field:SerializedName("CreatedBy")
		val createdBy: String,

		@field:SerializedName("EndTime")
		val endTime: Any,

		@field:SerializedName("UpdatedDate")
		val updatedDate: String,

		@field:SerializedName("PickUpLatitude")
		val pickUpLatitude: Double,

		@field:SerializedName("DropOffLongitude")
		val dropOffLongitude: Double,

		@field:SerializedName("StartTime")
		val startTime: String,

		@field:SerializedName("TripComment")
		val tripComment: Any,

		@field:SerializedName("UpdatedBy")
		val updatedBy: Any,

		@field:SerializedName("IsTripCompleted")
		val isTripCompleted: Boolean,

		@field:SerializedName("PickUpLongitude")
		val pickUpLongitude: Double,

		@field:SerializedName("TransportVisitID")
		val transportVisitID: Int,

		@field:SerializedName("TransportationGroupID")
		val transportationGroupID: Int,

		@field:SerializedName("CreatedDate")
		val createdDate: String,

		@field:SerializedName("DropOffLatitude")
		val dropOffLatitude: Double
	)
}
