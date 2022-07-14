package com.triare.idpjetpackandroid.data.remote.rest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.triare.idpjetpackandroid.data.BASE_URL
import com.triare.idpjetpackandroid.data.remote.rest.service.DownloadImageService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {

    private val gson: Gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    val apiService: DownloadImageService by lazy {
        retrofit.create(DownloadImageService::class.java)
    }
}