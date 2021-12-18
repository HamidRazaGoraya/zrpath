package com.hamid.template.network

import android.util.Log
import com.hamid.template.ui.loginAndRegister.models.LogInRequest
import com.hamid.template.utils.performGetOperation
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiDataSource: ApiDataSource,
) {
    fun signInUser(logInRequest: LogInRequest) = performGetOperation {
        apiDataSource.signInUser(logInRequest)
    }
}