package com.fpoly.shoes_app.framework.presentation.ui.shoes

import android.os.Parcelable
import com.fpoly.shoes_app.framework.domain.model.Category
import com.fpoly.shoes_app.framework.domain.model.Shoes
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShoesUiState(
    val categoriesSelected: List<Pair<Category, Boolean>>? = emptyList(),
    val popularShoes: List<Shoes>? = emptyList(),
    val isLoadingShoes: Boolean = false,
    val isLoadingCategories: Boolean = false,
) : Parcelable {
    val isLoading get() = isLoadingShoes || isLoadingCategories
}