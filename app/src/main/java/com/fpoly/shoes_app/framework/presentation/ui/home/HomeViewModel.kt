package com.fpoly.shoes_app.framework.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpoly.shoes_app.framework.domain.model.Category
import com.fpoly.shoes_app.framework.domain.usecase.GetCategoriesUseCase
import com.fpoly.shoes_app.framework.domain.usecase.GetShoesUseCase
import com.fpoly.shoes_app.utility.GET_POPULAR_SHOES_ALL
import com.fpoly.shoes_app.utility.ITEM_MORE
import com.fpoly.shoes_app.utility.SharedPreferencesManager
import com.fpoly.shoes_app.utility.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferencesManager,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getShoesUseCase: GetShoesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    init {
        getDataCategories()
        getDataPopularShoes()
    }

    private fun getDataCategories() {
        flow {
            emit(getCategoriesUseCase.invoke())
        }.onEach { resource ->
            when (resource.status) {
                Status.SUCCESS -> _uiState.update { state ->
                    state.copy(categories = updateCategoriesList(resource.data?.body()),
                        categoriesSelected = updateCategoriesSelectedList(resource.data?.body()).map {
                            if (it.id.isNullOrEmpty()) it to true
                            else it to false
                        })
                }

                Status.ERROR -> Log.e(
                    "HomeViewModel", "getDataCategories: Error ${resource.message}"
                )

                else -> {}
            }
        }.onStart {
            _uiState.update { it.copy(isLoading = true) }
        }.onCompletion {
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    private fun updateCategoriesList(categories: List<Category>?): List<Category> {
        val more = Category(
            image = "https://i.pinimg.com/564x/e7/65/04/e7650458fe434cd647eafb289a569fe2.jpg",
            name = ITEM_MORE
        )
        return when {
            categories == null -> emptyList()
            categories.size == CATEGORIES_SIZE_EQUALS_8 -> categories.take(CATEGORIES_SIZE_EQUALS_8)
            categories.size > CATEGORIES_SIZE_EQUALS_8 -> categories.take(
                CATEGORIES_SIZE_MORE_THAN_8
            ).plus(more)

            else -> categories
        }
    }

    private fun updateCategoriesSelectedList(categories: List<Category>?): List<Category> {
        val all = Category(
            name = "All"
        )
        val mutableCategoriesSelected = categories.orEmpty().toMutableList()
        mutableCategoriesSelected.add(0, all)
        return mutableCategoriesSelected
    }

    fun handleClickCategoriesSelected(category: Category) {
        val mutableCategoriesSelected = mutableListOf<Pair<Category, Boolean>>()
        uiState.value.categoriesSelected?.forEach {
            if (it.first == category) {
                mutableCategoriesSelected.add(Pair(category, true))
            } else {
                mutableCategoriesSelected.add(Pair(it.first, false))
            }
        }
        _uiState.update { state ->
            state.copy(categoriesSelected = mutableCategoriesSelected)
        }
    }

    //get shoes if sold > 0 and max 10 item
    fun getDataPopularShoes(category: String? = GET_POPULAR_SHOES_ALL) {
        flow {
            emit(getShoesUseCase.invoke())
        }.onEach { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    _uiState.update { state ->
                        val popularShoes = resource.data?.body()
                            ?.filter {
                                (it.sold ?: 0) > 0 &&
                                        (category == GET_POPULAR_SHOES_ALL || it.category?.name == category)
                            }
                            ?.sortedByDescending { it.sold }
                            ?.take(QUANTITY_POPULAR_SHOES)
                        state.copy(popularShoes = popularShoes)
                    }
                }

                Status.ERROR -> Log.e("HomeViewModel", "getDataShoes: Error ${resource.message}")
                else -> {}
            }
        }.onStart {
            _uiState.update { it.copy(isLoading = true) }
        }.onCompletion {
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    private companion object {
        private const val CATEGORIES_SIZE_EQUALS_8 = 8
        private const val CATEGORIES_SIZE_MORE_THAN_8 = 7
        private const val QUANTITY_POPULAR_SHOES = 10
    }
}