package com.hamid.template.network

object ApiEndPoint {

    const val apiStart = "api/"
    const val Employee="Employee/"
    const val Facility="Facility/"
    const val TransportationGroup="TransportationGroup/"
    const val Referral="Referral/"
    const val Document="Document/"

    const val LoginAPi= apiStart+"Security/Login"
    const val dashBoard= apiStart+"Security/Dashboard"
    const val reffrelDetails= apiStart+""

    const val EmployeeDetails= apiStart+ Employee+"GetEmployeeId"
    const val FacilityDetails= apiStart+ Facility+"GetLoggedInUserFacilitiesForTransportation"
    const val TodayTrip= apiStart+ TransportationGroup+"GetAssignedClientListForTransportationAssignment"
    const val OnGoingVisit= apiStart+ TransportationGroup+"OnGoingVisit"
    const val TripStart= apiStart+ TransportationGroup+"TripStart"
    const val TripClose= apiStart+ TransportationGroup+"TripClose"
    const val getUserCheckList= apiStart+ Referral+"GetReferralCheckList"
    const val getDocumentList= apiStart+ Document+"GetOrganizationFormList"
    const val SetMissingDocument= apiStart+ Document+"SetMissingDocument"
    const val saveCheckList= apiStart+ Referral+"SaveReferralCheckList"
    const val deleteCheckList= apiStart+ Referral+"DeleteReferralCheckList"
    const val getDocumentUrl= apiStart+ Document+"OpenNewOrbeonForm"
    const val saveTransportTime= apiStart+ TransportationGroup+"SetTransportVisitsTime"
    const val transportationDetail= apiStart+ TransportationGroup+"GetTransportVisitsDetail"
    const val saveForm= apiStart+ Document+"SaveOrbeonForm"
    const val savedDocumentList= apiStart+ Document+"GetDocumentList"
    const val openSavedForm= apiStart+ Document+"OpenSavedOrbeonForm"
    const val deleteDocument= apiStart+ Document+"DeleteDocument"
    const val GetReferralListForTransportationGroup= apiStart+ TransportationGroup+"GetReferralListForTransportationGroup"
    const val UserSaveSignature= apiStart+ TransportationGroup+"SaveReferralSignature"
    const val GetMissingDocumentList= apiStart+ Document+"GetMissingDocumentList"
    const val checkListCompleted= apiStart+ Referral+"SetChecklistComplete"
    const val UploadDocument= apiStart+ Document+"UploadDocument"
}