package com.fpoly.shoes_app.framework.presentation.ui.shoes

import com.fpoly.shoes_app.framework.domain.model.Category
import com.fpoly.shoes_app.framework.domain.model.Shoes

data class ShoesUiState(
    val categoriesSelected: List<Pair<Category, Boolean>>? = emptyList(),
    val popularShoes: List<Shoes>? = emptyList(),
    val isLoading: Boolean = false,
)