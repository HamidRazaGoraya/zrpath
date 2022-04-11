package com.hamid.template.ui.facilitiesPatiensts.models

import com.google.gson.annotations.SerializedName

data class RequestSaveCheck(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Key")
	val key: String
){
	data class Data(

		@field:SerializedName("Description")
		val description: String,

		@field:SerializedName("CheckListID")
		val CheckListID: Int,

		@field:SerializedName("TransportVisitID")
		val transportVisitID: Int,

		@field:SerializedName("EmployeeID")
		val employeeID: Int,

		@field:SerializedName("TransportVisitNoteID")
		val transportVisitNoteID: Int,
		@field:SerializedName("OrbeonFormID")
		val OrbeonFormID: String?,
	)
}


