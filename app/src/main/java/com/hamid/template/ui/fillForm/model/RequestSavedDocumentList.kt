package com.hamid.template.ui.fillForm.model

import com.google.gson.annotations.SerializedName

data class RequestSavedDocumentList(

	@field:SerializedName("Token")
	val token: String,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("Key")
	val key: String
)
{
	data class SearchParams(

		@field:SerializedName("ReferralID")
		val referralID: Int? = null,

		@field:SerializedName("ComplianceID")
		val complianceID: Int? = null,

		@field:SerializedName("DocumentName")
		val documentName: String? = null
	)

	data class Data(

		@field:SerializedName("SortType")
		val sortType: String? = null,

		@field:SerializedName("SearchParams")
		val searchParams: SearchParams? = null,

		@field:SerializedName("PageSize")
		val pageSize: Int? = null,

		@field:SerializedName("SortExpression")
		val sortExpression: String? = null,

		@field:SerializedName("PageIndex")
		val pageIndex: Int? = null
	)
}
