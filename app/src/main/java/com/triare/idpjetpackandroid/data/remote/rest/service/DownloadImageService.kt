package com.triare.idpjetpackandroid.data.remote.rest.service

import com.triare.idpjetpackandroid.data.IMAGE
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface DownloadImageService {

    @GET(IMAGE)
    suspend fun downloadImage(): Response<ResponseBody>
}