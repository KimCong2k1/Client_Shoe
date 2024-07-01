package com.fpoly.shoes_app.framework.domain.usecase

import com.fpoly.shoes_app.framework.repository.CategoriesRepository
import com.fpoly.shoes_app.utility.Resource
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val categoriesRepository: CategoriesRepository) {
    suspend operator fun invoke() = try {
        Resource.success(categoriesRepository.getCategories())
    } catch (e: Exception) {
        Resource.error(null, e.message)
    }
}