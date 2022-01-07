package com.hamid.template.ui.loginAndRegister.logResponseModel


import com.google.gson.annotations.SerializedName

data class LoginResponseX(
    @SerializedName("CachedData")
    val cachedData: CachedData,
    @SerializedName("EncyptedUserId")
    val encyptedUserId: String,
    @SerializedName("Token")
    val token: String
)