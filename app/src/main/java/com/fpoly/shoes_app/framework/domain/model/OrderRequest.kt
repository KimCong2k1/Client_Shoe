package com.fpoly.shoes_app.framework.domain.model

data class OrderRequest(
    val userId: String,
    val addressId: String? = null,
    val pay: String,
    val total: Long,
    val totalShip: Long,
    val items: List<ShoeOrder>,
)

data class ShoeOrder(
    val shoeId: String,
    val size: String,
    val color: String,
    val quantity: Int,
)