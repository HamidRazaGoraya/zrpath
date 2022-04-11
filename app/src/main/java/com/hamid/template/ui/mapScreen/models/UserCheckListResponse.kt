package com.hamid.template.ui.mapScreen.models

import com.google.gson.annotations.SerializedName

data class UserCheckListResponse(

	@field:SerializedName("IsSuccess")
	val isSuccess: Boolean,

	@field:SerializedName("Errors")
	val errors: Any,

	@field:SerializedName("Message")
	val message: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Code")
	val code: String
){


	data class Data(

		@field:SerializedName("CheckList")
		val checkList: List<CheckListItem>
	){
		data class CheckListItem(

			@field:SerializedName("TripDirection")
			val tripDirection: String,

			@field:SerializedName("IsRequired")
			val isRequired: Boolean,

			@field:SerializedName("CheckListID")
			val CheckListID: Int,

			@field:SerializedName("ReferralID")
			val referralID: Int,

			@field:SerializedName("Detail")
			val detail: String,

			@field:SerializedName("TransportVisitNoteID")
			val transportVisitNoteID: Int,

			@field:SerializedName("IsChecked")
			val isChecked: Boolean,
			@field:SerializedName("EBFormID")
			val EBFormID:String?
		)
	}
}


