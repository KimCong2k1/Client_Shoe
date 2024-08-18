package com.fpoly.shoes_app.framework.domain

data class FavoriteRequest(
    val userId: String? = null,
    val shoeId: String? = null,
)