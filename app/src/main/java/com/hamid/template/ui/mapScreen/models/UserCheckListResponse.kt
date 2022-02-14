package com.hamid.template.ui.mapScreen.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class UserCheckListResponse(
    @SerializedName("Code")
    @Expose
    val code: String, // 200
    @SerializedName("Data")
    @Expose
    val `data`: Data?,
    @SerializedName("Errors")
    @Expose
    val errors: List<Error>,
    @SerializedName("IsSuccess")
    @Expose
    val isSuccess: Boolean, // true
    @SerializedName("Message")
    @Expose
    val message: String
) {
    data class Data(
        @SerializedName("CheckList")
        @Expose
        val checkList: List<Check>
    ) {
        data class Check(
            @SerializedName("Detail")
            @Expose
            val detail: String, // Test1
            @SerializedName("IsChecked")
            @Expose
            val isChecked: Boolean, // false
            @SerializedName("IsRequired")
            @Expose
            val isRequired: Boolean, // false
            @SerializedName("ReferralCheckListMappingID")
            @Expose
            val referralCheckListMappingID: Int, // 1
            @SerializedName("ReferralID")
            @Expose
            val referralID: Int // 87099
        )
    }

    data class Error(
        @SerializedName("Field")
        @Expose
        val `field`: String, // string
        @SerializedName("Message")
        @Expose
        val message: String // string
    )
}