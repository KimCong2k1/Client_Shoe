package com.fpoly.shoes_app.framework.data.dataremove.api.getInterface

import com.fpoly.shoes_app.framework.domain.model.Category
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesApi {
    @GET("getalltype")
    suspend fun getCategories(): Response<List<Category>>
}