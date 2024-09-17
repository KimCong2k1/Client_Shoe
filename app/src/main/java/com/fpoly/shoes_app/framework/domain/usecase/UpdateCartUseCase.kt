package com.fpoly.shoes_app.framework.domain.usecase

import com.fpoly.shoes_app.framework.data.repository.CartRepository
import com.fpoly.shoes_app.utility.Resource
import javax.inject.Inject

class UpdateCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(id: String, numberShoe: Int) = try {
        Resource.success(cartRepository.updateCart(id, numberShoe))
    } catch (e: Exception) {
        Resource.error(null, e.message)
    }
}