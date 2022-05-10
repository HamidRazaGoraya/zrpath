package com.hamid.template.network

import com.hamid.template.ui.checkList.models.RequestMedicationFormsList
import com.hamid.template.ui.checkList.models.RequestSelfCheckList
import com.hamid.template.ui.facilitiesPatiensts.models.RequestDeleteCheck
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSaveCheck
import com.hamid.template.ui.facilitiesPatiensts.models.RequestSetTime
import com.hamid.template.ui.facilitiesPatiensts.models.RequestTransportDetails
import com.hamid.template.ui.fillForm.model.RequestDeleteDocument
import com.hamid.template.ui.fillForm.model.RequestSaveForm
import com.hamid.template.ui.fillForm.model.RequestSavedDocumentList
import com.hamid.template.ui.fillForm.model.RequestSavedOpenForm
import com.hamid.template.ui.mapScreen.models.RequestDocumentUrl
import com.hamid.template.ui.mapScreen.models.RequestTripClose
import com.hamid.template.ui.mapScreen.models.RequestTripStart
import com.hamid.template.ui.mapScreen.models.UserCheckListRequest
import com.hamid.template.ui.missingForms.model.RequestMissingDocument
import com.hamid.template.ui.missingForms.model.RequestUserMissingDocument
import com.hamid.template.ui.todayTripsList.models.RequestDashboardAPI
import com.hamid.template.ui.todayTripsList.models.RequestReferralList
import com.hamid.template.utils.performGetOperation
import java.io.File
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
    fun getUserCheckList(data: UserCheckListRequest.Data)= performGetOperation {
        apiDataSource.getUserCheckList(data)
    }
    fun getDocumentList()= performGetOperation {
        apiDataSource.getDocumentList()
    }
    fun SetMissingDocument(data: RequestUserMissingDocument.Data)= performGetOperation {
        apiDataSource.SetMissingDocument(data)
    }
    fun getDocumentUrl(data: RequestDocumentUrl.Data)= performGetOperation {
        apiDataSource.getDocumentUrl(data)
    }
    fun getSaveCheckPoint(data: RequestSaveCheck.Data)= performGetOperation {
        apiDataSource.getSaveCheckPoint(data)
    }
    fun deleteCheckList(data:RequestDeleteCheck.Data)= performGetOperation {
        apiDataSource.deleteCheckList(data)
    }
    fun saveTTime(data: RequestSetTime.Data)= performGetOperation {
        apiDataSource.saveTransportTime(data)
    }
    fun requestTransportDetails(data:RequestTransportDetails.Data)= performGetOperation {
        apiDataSource.requestTransportDetails(data)
    }
    fun requestSaveForm(data:RequestSaveForm.Data)= performGetOperation {
        apiDataSource.requestSaveForm(data)
    }
    fun getSavedDocumentsList(data:RequestSavedDocumentList.Data)= performGetOperation {
        apiDataSource.getSavedDocuments(data)
    }
    fun openSavedForm(data: RequestSavedOpenForm.Data)= performGetOperation {
        apiDataSource.openSavedForm(data)
    }
    fun deleteDocument(data:RequestDeleteDocument.Data)= performGetOperation {
        apiDataSource.deleteDocument(data)
    }
    fun GetReferralListForTransportationGroup(data: RequestReferralList.Data)= performGetOperation {
        apiDataSource.GetReferralListForTransportationGroup(data)
    }
    fun getDashboard(data: RequestDashboardAPI.Data)= performGetOperation {
        apiDataSource.getDashBoard(data)
    }
    fun OnGoingVisit(ScheduleID:Int,referralID:Int,transportationGroupID:Int)= performGetOperation {
        apiDataSource.OnGoingVisit(ScheduleID,referralID,transportationGroupID)
    }
    fun TripStart(data: RequestTripStart.Data)= performGetOperation {
        apiDataSource.TripStart(data)
    }
    fun TripClose(data: RequestTripClose.Data)= performGetOperation {
        apiDataSource.TripClose(data)
    }
    fun GetMissingDocumentList(data: RequestMissingDocument.Data)= performGetOperation {
        apiDataSource.GetMissingDocumentList(data)
    }
    fun checkListCompleted(data: RequestSelfCheckList.Data)= performGetOperation {
        apiDataSource.checkListCompleted(data)
    }
    fun saveUserSignature(TransportVisitIDValue:Int,ReferralIDValue:Int,ScheduleIDValue:Int,file: File)= performGetOperation {
            apiDataSource.saveUserSignature(TransportVisitIDValue, ReferralIDValue, ScheduleIDValue, file)
    }
    fun UploadDocument(fileName:String,ReferralIDValue:Int,KindOfDocument:String,file: File)= performGetOperation {
        apiDataSource.UploadDocument( fileName,ReferralIDValue, KindOfDocument, file)
    }
    fun GetMedicationFormList(data: RequestMedicationFormsList.Data)= performGetOperation {
        apiDataSource.GetMedicationFormList(data)
    }
    fun getNewDashboard(date:String,facilityId:Int) = performGetOperation {
        apiDataSource.getNewDashboard(date,facilityId)
    }
}