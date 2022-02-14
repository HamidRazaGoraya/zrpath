package com.hamid.template.network

object ApiEndPoint {

    const val apiStart = "api/"

    const val LoginAPi= apiStart+"Security/Login"
    const val EmployeeDetails= apiStart+"Employee/GetEmployeeId"
    const val FacilityDetails= apiStart+"Facility/GetLoggedInUserFacilitiesForTransportation"
    const val TodayTrip= apiStart+"TransportationGroup/GetAssignedClientListForTransportationAssignment"
    const val getUserCheckList= apiStart+"Referral/GetReferralCheckList"
    const val getDocumentList= apiStart+"Document/GetOrganizationFormList"
    const val getDocumentUrl= apiStart+"Document/OpenNewOrbeonForm"
}