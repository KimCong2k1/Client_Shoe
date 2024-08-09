package com.fpoly.shoes_app.framework.data.dataremove.api

import com.fpoly.shoes_app.framework.domain.model.favourite.Favourites
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FavouritesApi {
    @GET("findfavourite/{id}")
    suspend fun getFavourites(
        @Path("id") userId: String
    ): Response<List<Favourites>>
}