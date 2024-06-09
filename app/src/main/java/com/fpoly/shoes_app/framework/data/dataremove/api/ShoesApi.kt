package com.fpoly.shoes_app.framework.data.dataremove.api

import com.fpoly.shoes_app.framework.domain.model.Shoes
import retrofit2.Response
import retrofit2.http.GET

interface ShoesApi {
    @GET("getallproduct")
    suspend fun getShoes(): Response<List<Shoes>>
}