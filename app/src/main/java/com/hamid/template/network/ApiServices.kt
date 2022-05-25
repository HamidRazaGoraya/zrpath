package com.hamid.template.network

import com.hamid.template.ui.checkList.models.RequestMedicationFormsList
import com.hamid.template.ui.checkList.models.RequestSelfCheckList
import com.hamid.template.ui.checkList.models.ResponseSelfCheckList
import com.hamid.template.ui.dashboard.models.*
import com.hamid.template.ui.facilitiesPatiensts.models.*
import com.hamid.template.ui.fillForm.model.RequestDeleteDocument
import com.hamid.template.ui.fillForm.model.RequestSaveForm
import com.hamid.template.ui.fillForm.model.RequestSavedDocumentList
import com.hamid.template.ui.fillForm.model.RequestSavedOpenForm
import com.hamid.template.ui.loginAndRegister.logInRequestModel.LogInRequest
import com.hamid.template.ui.loginAndRegister.logResponseModel.LogInResponse
import com.hamid.template.ui.mapScreen.models.*
import com.hamid.template.ui.medicationFormsList.model.ResponseMedicationFormsList
import com.hamid.template.ui.missingForms.model.RequestMissingDocument
import com.hamid.template.ui.missingForms.model.RequestUserMissingDocument
import com.hamid.template.ui.missingForms.model.ResponseMissingDocument
import com.hamid.template.ui.missingForms.model.ResponseUserMissingDocument
import com.hamid.template.ui.todayTripDetails.models.RequestOnGoingVisit
import com.hamid.template.ui.todayTripDetails.models.ResponseOnGoingVisit
import com.hamid.template.ui.todayTripsList.models.RequestDashboardAPI
import com.hamid.template.ui.todayTripsList.models.RequestReferralList
import com.hamid.template.ui.todayTripsList.models.ResponseReferralList
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @POST(ApiEndPoint.SetMissingDocument)
    suspend fun SetMissingDocument(
        @Body requestUserMissingDocument: RequestUserMissingDocument
    ): Response<ResponseUserMissingDocument>
    @POST(ApiEndPoint.getDocumentUrl)
    suspend fun getDocumentUrl(
        @Body requestDocumentUrl: RequestDocumentUrl
    ): Response<ResponseDocumentUrl>


    @POST(ApiEndPoint.saveCheckList)
    suspend fun saveCheckList(
        @Body requestSaveCheck: RequestSaveCheck
    ):Response<ResponseSaveCheck>

    @POST(ApiEndPoint.deleteCheckList)
    suspend fun deleteCheckList(
        @Body requestDeleteCheck: RequestDeleteCheck
    ):Response<ResponseSaveCheck>
    @POST(ApiEndPoint.saveTransportTime)
    suspend fun saveTransportTime(
        @Body requestSetTTime: RequestSetTime
    ):Response<ResponseSaveTime>
    @POST(ApiEndPoint.transportationDetail)
    suspend fun getTransportationDetails(
        @Body requestTransportDetails: RequestTransportDetails
    ):Response<ResponseTripDetails>
    @POST(ApiEndPoint.saveForm)
    suspend fun saveForm(
        @Body requestSaveForm: RequestSaveForm
    ):Response<ErrorMessage>

    @POST(ApiEndPoint.savedDocumentList)
    suspend fun savedDocumentList(
        @Body requestSavedDocumentList: RequestSavedDocumentList
    ):Response<ErrorMessage>
    @POST(ApiEndPoint.openSavedForm)
    suspend fun openSavedForm(
        @Body requestSavedOpenForm: RequestSavedOpenForm
    ):Response<ResponseDocumentUrl>
    @POST(ApiEndPoint.deleteDocument)
    suspend fun deleteDocument(
        @Body requestDeleteDocument: RequestDeleteDocument
    ):Response<ErrorMessage>


    @POST(ApiEndPoint.GetReferralListForTransportationGroup)
    suspend fun GetReferralListForTransportationGroup(
        @Body requestReferralList: RequestReferralList
    ):Response<ResponseReferralList>

    @POST(ApiEndPoint.dashBoard)
    suspend fun getDashBoard(
        @Body requestDashboardAPI: RequestDashboardAPI
    ):Response<ResponseDashBoard>

    @POST(ApiEndPoint.OnGoingVisit)
    suspend fun OnGoingVisit(
        @Body requestOnGoingVisit: RequestOnGoingVisit
    ):Response<ResponseOnGoingVisit>

    @POST(ApiEndPoint.TripStart)
    suspend fun TripStart(
        @Body tripStart: RequestTripStart
    ):Response<ResponseTripStart>

    @POST(ApiEndPoint.TripClose)
    suspend fun TripClose(
        @Body requestTripClose: RequestTripClose
    ):Response<ResponseTripClose>

    @Multipart
    @POST(ApiEndPoint.UserSaveSignature)
    suspend fun saveUserSignature(
        @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part? = null
    ): Response<ErrorMessage>

    @Multipart
    @POST(ApiEndPoint.ChildSaveSignature)
    suspend fun saveChildSignature(
        @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part? = null
    ): Response<ErrorMessage>

    @POST(ApiEndPoint.GetMissingDocumentList)
    suspend fun GetMissingDocumentList(
        @Body requestMissingDocument: RequestMissingDocument
    ):Response<ResponseMissingDocument>
    @POST(ApiEndPoint.checkListCompleted)
    suspend fun checkListCompleted(
        @Body requestSelfCheckList: RequestSelfCheckList
    ):Response<ResponseSelfCheckList>

    @Multipart
    @POST(ApiEndPoint.UploadDocument)
    suspend fun UploadDocument(
        @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part
    ): Response<ErrorMessage>

    @POST(ApiEndPoint.GetMedicationFormList)
    suspend fun GetMedicationFormList(
        @Body requestMedicationFormsList: RequestMedicationFormsList
    ):Response<ResponseMedicationFormsList>

    @POST(ApiEndPoint.GetDashboard)
    suspend fun getNewDashBoard(
        @Body todayTripRequest: TodayTripRequest
    ): Response<TodayTripResponse>
}