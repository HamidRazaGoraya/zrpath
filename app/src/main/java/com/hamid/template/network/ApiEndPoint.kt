package com.hamid.template.network

object ApiEndPoint {

    const val apiStart = "api/"

    const val LoginAPi= apiStart+"Security/Login"
    const val EmployeeDetails= apiStart+"Employee/GetEmployeeId"
    const val FacilityDetails= apiStart+"Facility/GetLoggedInUserFacilitiesForTransportation"
    const val FacilityList= apiStart+"Facility/GetLoggedInUserFacilitiesForTransportation"
}