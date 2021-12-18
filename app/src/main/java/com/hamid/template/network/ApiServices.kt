package com.hamid.template.network

import com.hamid.template.ui.loginAndRegister.models.LogInRequest
import com.hamid.template.ui.loginAndRegister.models.LogInResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiServices {
    @POST(ApiEndPoint.LoginAPi)
    suspend fun signInUser(
        @Body logInRequest: LogInRequest
    ): Response<LogInResponse>
}