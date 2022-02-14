package com.hamid.template.network

import android.content.Context
import android.os.Build
import com.hamid.template.ui.dashboard.models.FacilitiesRequestModel
import com.hamid.template.ui.dashboard.models.RequestEmployDetails
import com.hamid.template.ui.mapScreen.models.RequestDocumentList
import com.hamid.template.ui.facilitiesPatiensts.models.TodayTripRequest
import com.hamid.template.ui.loginAndRegister.logInRequestModel.LogInRequest
import com.hamid.template.ui.mapScreen.models.RequestDocumentUrl
import com.hamid.template.ui.mapScreen.models.UserCheckListRequest
import com.hamid.template.utils.Constants
import com.hamid.template.utils.SharedPreferenceManager
import com.hamid.template.utils.getDateValue
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

    suspend fun getUserCheckList(ScheduleID:Int,ReferralID:Int) = getResult {
        sharedPreferenceManager.UserLogInResponse.let {
            val data=UserCheckListRequest.Data(Calendar.getInstance().getDateValue(),it?.data!!.employee.employeeID,ReferralID,ScheduleID)
            apiServices.getUserCheckList(UserCheckListRequest(data,Constants.Key,sharedPreferenceManager.getToken))
        }
    }
    suspend fun getDocumentList() = getResult {
        apiServices.getDocumentList(RequestDocumentList(Constants.Key,sharedPreferenceManager.getToken))
    }

    suspend fun getDocumentUrl(EBFormID:String,ReferralID:Int,SavePreference:Boolean) = getResult {
        val data=RequestDocumentUrl.Data(SavePreference,ReferralID,EBFormID)
        apiServices.getDocumentUrl(RequestDocumentUrl(sharedPreferenceManager.getToken,data,Constants.Key))
    }

}