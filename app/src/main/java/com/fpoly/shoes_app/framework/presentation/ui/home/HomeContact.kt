package com.fpoly.shoes_app.framework.presentation.ui.home

import com.fpoly.shoes_app.framework.domain.model.Category
import com.fpoly.shoes_app.framework.domain.model.Shoes

data class HomeUiState(
    val categories: List<Category>? = emptyList(),
    val categoriesSelected: List<Pair<Category, Boolean>>? = emptyList(),
    val shoes: List<Shoes>? = emptyList(),
    val isLoading: Boolean = false,
)