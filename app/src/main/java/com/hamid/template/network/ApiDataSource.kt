package com.hamid.template.network

import android.content.Context
import android.util.Log
import com.hamid.template.utils.SharedPreferenceManager
import com.google.gson.Gson
import com.hamid.template.ui.loginAndRegister.models.LogInRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val apiServices: ApiServices,
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val context: Context
) : BaseDataSource(context) {
    suspend fun signInUser(logInRequest: LogInRequest) = getResult {
        apiServices.signInUser(logInRequest)
    }


}