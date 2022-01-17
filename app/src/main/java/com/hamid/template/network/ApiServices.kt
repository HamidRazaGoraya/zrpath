package com.hamid.template.network

import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.dashboard.models.FacilitiesRequestModel
import com.hamid.template.ui.dashboard.models.RequestEmployDetails
import com.hamid.template.ui.dashboard.models.ResponseEmployDetails
import com.hamid.template.ui.loginAndRegister.logInRequestModel.LogInRequest
import com.hamid.template.ui.loginAndRegister.logResponseModel.LogInResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiServices {
    @POST(ApiEndPoint.LoginAPi)
    suspend fun signInUser(
        @Body logInRequest: LogInRequest
    ): Response<LogInResponse>

    @POST(ApiEndPoint.EmployeeDetails)
    suspend fun getEmployeeDetails(
        @Body requestEmployDetails: RequestEmployDetails
    ): Response<ResponseEmployDetails>
    @POST(ApiEndPoint.FacilityDetails)
    suspend fun getFacilityDetails(
        @Body facilitiesRequestModel: FacilitiesRequestModel
    ): Response<AllFacilitiesModel>
}