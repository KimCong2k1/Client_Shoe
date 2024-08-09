package com.fpoly.shoes_app.framework.retrofitGeneral

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGeneral {
        private const val BASE_URL = "http://192.168.1.66:3000/api/"
        val retrofitInstance: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
