package com.hamid.template.network

import com.hamid.template.ui.loginAndRegister.logInRequestModel.LogInRequest
import com.hamid.template.ui.loginAndRegister.logResponseModel.LogInResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiServices {
    @POST(ApiEndPoint.LoginAPi)
    suspend fun signInUser(
        @Body logInRequest: LogInRequest
    ): Response<LogInResponse>
}