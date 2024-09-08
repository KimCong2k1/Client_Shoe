package com.fpoly.shoes_app.framework.domain.usecase

import com.fpoly.shoes_app.framework.data.repository.FavoriteRepository
import com.fpoly.shoes_app.framework.domain.FavoriteRequest
import com.fpoly.shoes_app.utility.Resource
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(favoriteRequest: FavoriteRequest) = try {
        Resource.success(favoriteRepository.addFavorite(favoriteRequest))
    } catch (e: Exception) {
        Resource.error(null, e.message)
    }
}