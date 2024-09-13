package com.fpoly.shoes_app.framework.presentation.ui.cart

import com.fpoly.shoes_app.framework.domain.model.Carts

data class CartUiState(
    val isLoading: Boolean = false,
    val carts: Carts? = null,
    val numberShoe: Int = 0,
) {
    val shoes = carts?.cart
    val isEnableButton = (shoes?.size ?: 0) > 0
    val total = if ((shoes?.size ?: 0) > 0) {
        shoes?.fold(0L) { acc, shoeWrapper ->
            val price = shoeWrapper.shoe?.price ?: 0L
            val numberShoe = shoeWrapper.shoe?.numberShoe ?: 0
            acc + (numberShoe * price)
        }
    } else 0
}