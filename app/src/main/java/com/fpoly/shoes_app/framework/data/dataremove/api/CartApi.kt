package com.fpoly.shoes_app.framework.data.dataremove.api

import com.fpoly.shoes_app.framework.domain.model.CartRequest
import com.fpoly.shoes_app.framework.domain.model.Carts
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApi {
    @GET("cartList/{id}")
    suspend fun getCart(
        @Path("id") id: String
    ): Response<Carts>

    @POST("addCart")
    suspend fun addCart(
        @Body cartRequest: CartRequest,
    ): Response<Any>

    @POST("updateNumberShoe/{id}")
    suspend fun updateCart(
        @Body numberShoe: Int,
        @Path("id") id: String,
    ): Response<Any>

    @DELETE("deleteCart/{id}")
    suspend fun removeCart(
        @Path("id") id: String
    ): Response<Any>
}