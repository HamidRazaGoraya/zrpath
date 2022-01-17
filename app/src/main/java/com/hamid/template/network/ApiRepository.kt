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
    fun getFacilityDetails(direction:String) = performGetOperation {
        apiDataSource.getFacilityDetails(direction)
    }
}