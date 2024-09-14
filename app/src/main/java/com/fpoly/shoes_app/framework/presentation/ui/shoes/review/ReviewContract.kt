package com.fpoly.shoes_app.framework.presentation.ui.shoes.review

import com.fpoly.shoes_app.framework.domain.model.Comment

data class ReviewUiState(
    val isLoading: Boolean = false,
    val reviews: List<Comment>? = emptyList(),
)