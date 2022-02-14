package com.hamid.template.network

import com.hamid.template.utils.performGetOperation
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiDataSource: ApiDataSource,
) {
    fun signInUser(userName:String,pasword:String) = performGetOperation {
        apiDataSource.signInUser(userName,pasword)
    }
    fun getEmployeeDetails() = performGetOperation {
        apiDataSource.getEmployeeDetails()
    }
    fun getFacilityDetails() = performGetOperation {
        apiDataSource.getFacilityDetails()
    }
    fun getTodayTrip(date:String,facilityId:Int) = performGetOperation {
        apiDataSource.getTodayTrip(date,facilityId)
    }
    fun getUserCheckList(ScheduleID:Int,ReferralID:Int)= performGetOperation {
        apiDataSource.getUserCheckList(ScheduleID, ReferralID)
    }
    fun getDocumentList()= performGetOperation {
        apiDataSource.getDocumentList()
    }
    fun getDocumentUrl(EBFormID:String,ReferralID:Int,SavePreference:Boolean)= performGetOperation {
        apiDataSource.getDocumentUrl(EBFormID, ReferralID, SavePreference)
    }
}