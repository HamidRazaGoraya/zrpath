package com.hamid.template.network

import android.content.Context
import android.os.Build
import com.hamid.template.ui.loginAndRegister.fragments.LoginFragment
import com.hamid.template.ui.loginAndRegister.logInRequestModel.LogInRequest
import com.hamid.template.utils.Constants
import com.hamid.template.utils.SharedPreferenceManager
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


}