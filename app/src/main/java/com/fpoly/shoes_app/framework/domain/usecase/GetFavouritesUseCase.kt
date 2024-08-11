package com.fpoly.shoes_app.framework.domain.usecase

import com.fpoly.shoes_app.framework.repository.FavouritesRepository
import com.fpoly.shoes_app.utility.Resource
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(private val favouritesRepository: FavouritesRepository) {
    suspend operator fun invoke() = try {
        Resource.success(favouritesRepository.getFavourites())
    } catch (e: Exception) {
        Resource.error(null, e.message)
    }
}