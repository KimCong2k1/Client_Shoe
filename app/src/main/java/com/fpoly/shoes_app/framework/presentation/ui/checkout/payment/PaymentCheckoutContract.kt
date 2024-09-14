package com.fpoly.shoes_app.framework.presentation.ui.checkout.payment

import com.fpoly.shoes_app.framework.domain.model.PaymentArgs
import com.fpoly.shoes_app.framework.domain.model.ShoeData
import com.fpoly.shoes_app.framework.domain.model.ShoeOrder
import com.fpoly.shoes_app.framework.domain.model.ShoesCart

data class PaymentCheckoutUiState(
    val isLoading: Boolean = false,
    val args: PaymentArgs? = null,
) {
    val shoeOrder = args?.shoesCart?.toListOrderRequest() ?: emptyList()
}

fun List<ShoesCart>.toListOrderRequest(): List<ShoeOrder> =
    this.map { it.shoe.toOrderRequest() }

fun ShoeData?.toOrderRequest(): ShoeOrder = ShoeOrder(
    shoeId = this?.idShoe.orEmpty(),
    size = this?.size.orEmpty(),
    color = this?.color?.textColor.orEmpty(),
    quantity = this?.numberShoe ?: 0,
)

sealed interface PaymentCheckoutSingleEvent {
    data object CheckOut : PaymentCheckoutSingleEvent
}