package com.hamid.template.network

import android.util.Log
import com.hamid.template.ui.dashboard.entery.RequestUserCv
import com.hamid.template.utils.performGetOperation
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiDataSource: ApiDataSource,
) {
    fun generateCv(requestUserCv: RequestUserCv) = performGetOperation {
        Log.i("DataAdded","yes2")
        apiDataSource.generateCv(requestUserCv)
    }
}