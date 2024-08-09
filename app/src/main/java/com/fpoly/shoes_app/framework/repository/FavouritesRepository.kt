package com.fpoly.shoes_app.framework.repository

import com.fpoly.shoes_app.framework.data.dataremove.api.FavouritesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavouritesRepository @Inject constructor(val favouritesApi: FavouritesApi) {
    suspend fun getFavourites(userId: String) = withContext(Dispatchers.IO) {
        favouritesApi.getFavourites(userId)
    }
}