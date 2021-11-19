package com.hamid.template.network

import android.content.Context
import android.util.Log
import com.hamid.template.ui.dashboard.entery.RequestUserCv
import com.hamid.template.utils.SharedPreferenceManager
import com.google.gson.Gson
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

    suspend fun generateCv(requestUserCv: RequestUserCv) = getResult {
        val body: MultipartBody.Part? = null
        val comment: RequestBody = createPartFromString(Gson().toJson(requestUserCv))
        val lang: RequestBody = createPartFromString(sharedPreferenceManager.getWeblanguage!!)
        val map: HashMap<String, RequestBody> = HashMap()
        map["form"] = comment
        map["lang"] =lang
        Log.i("DataAdded","yes")
        apiServices.generateCv(
            map,
            body
        )
    }
    private fun createPartFromString(s: String): RequestBody {
        return s.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}