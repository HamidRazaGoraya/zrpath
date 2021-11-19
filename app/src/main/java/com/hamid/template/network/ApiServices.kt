package com.hamid.template.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface ApiServices {
    @Multipart
    @POST(ApiEndPoint.GENERATE_CV)
    suspend fun generateCv(
        @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part? = null
    ): Response<ErrorMessage>
}