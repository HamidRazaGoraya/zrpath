package com.hamid.template.network

import com.hamid.template.ui.dashboard.models.AllFacilitiesModel
import com.hamid.template.ui.dashboard.models.FacilitiesRequestModel
import com.hamid.template.ui.dashboard.models.RequestEmployDetails
import com.hamid.template.ui.dashboard.models.ResponseEmployDetails
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripRequest
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripResponse
import com.hamid.template.ui.loginAndRegister.logInRequestModel.LogInRequest
import com.hamid.template.ui.loginAndRegister.logResponseModel.LogInResponse
import com.hamid.template.ui.mapScreen.models.*
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

    @POST(ApiEndPoint.TodayTrip)
    suspend fun getTodayTrip(
        @Body todayTripRequest: TodayTripRequest
    ): Response<TodayTripResponse>

    @POST(ApiEndPoint.getUserCheckList)
    suspend fun getUserCheckList(
        @Body userCheckListRequest: UserCheckListRequest
    ): Response<UserCheckListResponse>

    @POST(ApiEndPoint.getDocumentList)
    suspend fun getDocumentList(
        @Body requestDocumentList: RequestDocumentList
    ): Response<ResponseDocumentList>

    @POST(ApiEndPoint.getDocumentUrl)
    suspend fun getDocumentUrl(
        @Body requestDocumentUrl: RequestDocumentUrl
    ): Response<ResponseDocumentUrl>
}