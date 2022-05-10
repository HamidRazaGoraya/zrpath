package com.hamid.template.network

import android.content.Context
import android.os.Build
import com.hamid.template.ui.checkList.models.RequestMedicationFormsList
import com.hamid.template.ui.checkList.models.RequestSelfCheckList
import com.hamid.template.ui.dashboard.models.FacilitiesRequestModel
import com.hamid.template.ui.dashboard.models.RequestEmployDetails
import com.hamid.template.ui.facilitiesPatiensts.models.*
import com.hamid.template.ui.fillForm.model.RequestDeleteDocument
import com.hamid.template.ui.fillForm.model.RequestSaveForm
import com.hamid.template.ui.fillForm.model.RequestSavedDocumentList
import com.hamid.template.ui.fillForm.model.RequestSavedOpenForm
import com.hamid.template.ui.loginAndRegister.logInRequestModel.LogInRequest
import com.hamid.template.ui.mapScreen.models.*
import com.hamid.template.ui.missingForms.model.RequestMissingDocument
import com.hamid.template.ui.missingForms.model.RequestUserMissingDocument
import com.hamid.template.ui.todayTripDetails.models.RequestOnGoingVisit
import com.hamid.template.ui.todayTripsList.models.RequestDashboardAPI
import com.hamid.template.ui.todayTripsList.models.RequestReferralList
import com.hamid.template.utils.Constants
import com.hamid.template.utils.SharedPreferenceManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val apiServices: ApiServices,
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val context: Context
) : BaseDataSource(context) {
    var DeviceOSVersion=Build.VERSION.SDK_INT.toString()
    suspend fun signInUser(userName:String,pasword:String) = getResult {

        val data=LogInRequest.Data(DeviceOSVersion,Constants.DeviceType,Constants.DeviceUDID,true,pasword,userName)

        apiServices.signInUser(LogInRequest(data,Constants.Key,sharedPreferenceManager.getToken))
    }
    suspend fun getEmployeeDetails() = getResult {
        sharedPreferenceManager.UserLogInResponse.let {
            val data=RequestEmployDetails.Data(it?.data!!.employee.employeeID)
            apiServices.getEmployeeDetails(RequestEmployDetails(data,Constants.Key,sharedPreferenceManager.getToken))
        }
    }
    suspend fun getFacilityDetails() = getResult {
        sharedPreferenceManager.UserLogInResponse.let {
            val data=FacilitiesRequestModel.Data(it?.data!!.employee.employeeID,sharedPreferenceManager.getTripType)
            apiServices.getFacilityDetails(FacilitiesRequestModel(data,Constants.Key,sharedPreferenceManager.getToken))
        }

    }
    suspend fun getTodayTrip(date:String,facilityId: Int) = getResult {
        sharedPreferenceManager.UserLogInResponse.let {
            val data=TodayTripRequest.Data(date,it?.data!!.employee.employeeID,facilityId,sharedPreferenceManager.getTripType)
            apiServices.getTodayTrip(TodayTripRequest(data,Constants.Key,sharedPreferenceManager.getToken))
        }

    }

    suspend fun getUserCheckList(data:UserCheckListRequest.Data) = getResult {
        sharedPreferenceManager.UserLogInResponse.let {
            apiServices.getUserCheckList(UserCheckListRequest(data,Constants.Key,sharedPreferenceManager.getToken))
        }
    }
    suspend fun getDocumentList() = getResult {
        apiServices.getDocumentList(RequestDocumentList(Constants.Key,sharedPreferenceManager.getToken))
    }
    suspend fun SetMissingDocument(data:RequestUserMissingDocument.Data) = getResult {
        apiServices.SetMissingDocument(RequestUserMissingDocument(sharedPreferenceManager.getToken,data,Constants.Key))
    }

    suspend fun getDocumentUrl(data: RequestDocumentUrl.Data) = getResult {
        apiServices.getDocumentUrl(RequestDocumentUrl(sharedPreferenceManager.getToken,data,Constants.Key))
    }

    suspend fun getSaveCheckPoint(data:RequestSaveCheck.Data)=getResult {
       apiServices.saveCheckList(RequestSaveCheck(sharedPreferenceManager.getToken,data,Constants.Key))
    }

    suspend fun deleteCheckList(data:RequestDeleteCheck.Data)=getResult {
        apiServices.deleteCheckList(RequestDeleteCheck(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun saveTransportTime(data:RequestSetTime.Data)=getResult {
        apiServices.saveTransportTime(RequestSetTime(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun requestTransportDetails(data:RequestTransportDetails.Data)=getResult {
        apiServices.getTransportationDetails(RequestTransportDetails(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun requestSaveForm(data:RequestSaveForm.Data)=getResult {
        apiServices.saveForm(RequestSaveForm(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun getSavedDocuments(data:RequestSavedDocumentList.Data)=getResult {
        apiServices.savedDocumentList(RequestSavedDocumentList(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun openSavedForm(data:RequestSavedOpenForm.Data)=getResult {
        apiServices.openSavedForm(RequestSavedOpenForm(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun deleteDocument(data:RequestDeleteDocument.Data)=getResult {
        apiServices.deleteDocument(RequestDeleteDocument(sharedPreferenceManager.getToken, data,Constants.Key))
    }
    suspend fun GetReferralListForTransportationGroup(data:RequestReferralList.Data)=getResult {
        apiServices.GetReferralListForTransportationGroup(RequestReferralList(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun getDashBoard(data: RequestDashboardAPI.Data)=getResult {
        apiServices.getDashBoard(RequestDashboardAPI(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun OnGoingVisit(ScheduleID:Int, referralID:Int, transportationGroupID:Int)=getResult {
        val data=RequestOnGoingVisit.Data(ScheduleID,sharedPreferenceManager.getEmployID(),referralID,transportationGroupID)
        apiServices.OnGoingVisit(RequestOnGoingVisit(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun TripStart(data:RequestTripStart.Data)=getResult {
        apiServices.TripStart(RequestTripStart(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun TripClose(data:RequestTripClose.Data)=getResult {
        apiServices.TripClose(RequestTripClose(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun GetMissingDocumentList(data:RequestMissingDocument.Data)=getResult{
        apiServices.GetMissingDocumentList(RequestMissingDocument(sharedPreferenceManager.getToken,data,Constants.Key))
    }
    suspend fun checkListCompleted(data:RequestSelfCheckList.Data)=getResult{
        apiServices.checkListCompleted(RequestSelfCheckList(sharedPreferenceManager.getToken,data,Constants.Key))
    }

    suspend fun saveUserSignature(TransportVisitIDValue:Int,ReferralIDValue:Int,ScheduleIDValue:Int,file: File) = getResult {
        var body: MultipartBody.Part? = null
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

        body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val Key: RequestBody = createPartFromString(Constants.Key)
        val Token: RequestBody = createPartFromString(sharedPreferenceManager.getToken)
        val ScheduleID: RequestBody = createPartFromString(ScheduleIDValue.toString())
        val ReferralID: RequestBody = createPartFromString(ReferralIDValue.toString())
        val TransportVisitID: RequestBody = createPartFromString(TransportVisitIDValue.toString())

        val map: HashMap<String, RequestBody> = HashMap()
        map["Key"] = Key
        map["Token"] = Token
        map["ScheduleID"] = ScheduleID
        map["ReferralID"] = ReferralID
        map["TransportVisitID"] = TransportVisitID


        apiServices.saveUserSignature(
            map,
            body
        )
    }
    private fun createPartFromString(s: String): RequestBody {
        return s.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    suspend fun UploadDocument(fileName:String,ReferralIDValue:Int,DocumentType:String,file: File) = getResult {

        var body: MultipartBody.Part? = null
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

        body = MultipartBody.Part.createFormData("File", file.name, requestFile)

        val map: HashMap<String, RequestBody> = HashMap()
        map["Key"] = createPartFromString(Constants.Key)
        map["Token"] = createPartFromString(sharedPreferenceManager.getToken)
        map["ComplianceID"] =  createPartFromString("-1")
        map["ReferralDocumentID"]=createPartFromString("0")
        map["ReferralID"] = createPartFromString(ReferralIDValue.toString())
        map["FileName"] = createPartFromString(fileName)
        map["FileType"] =createPartFromString("."+file.extension)
        map["KindOfDocument"]   =  createPartFromString(DocumentType)
        apiServices.UploadDocument(
            map,
            body
        )
    }


    suspend fun GetMedicationFormList(data:RequestMedicationFormsList.Data)=getResult{
        apiServices.GetMedicationFormList(RequestMedicationFormsList(sharedPreferenceManager.getToken,data,Constants.Key))
    }

    suspend fun getNewDashboard(date:String,facilityId: Int) = getResult {
        sharedPreferenceManager.UserLogInResponse.let {
            val data=TodayTripRequest.Data(date,it?.data!!.employee.employeeID,facilityId,sharedPreferenceManager.getTripType)
            apiServices.getNewDashBoard(TodayTripRequest(data,Constants.Key,sharedPreferenceManager.getToken))
        }

    }

}