package com.fpoly.shoes_app.framework.presentation.ui.shoes.shoesdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpoly.shoes_app.framework.domain.usecase.GetShoeDetailUseCase
import com.fpoly.shoes_app.framework.presentation.ui.shoes.shoesdetail.ShoeDetailFragment.Companion.MAX_SHOE
import com.fpoly.shoes_app.framework.presentation.ui.shoes.shoesdetail.ShoeDetailFragment.Companion.PLUS
import com.fpoly.shoes_app.framework.presentation.ui.shoes.shoesdetail.ShoeDetailFragment.Companion.REDUCE
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoeDetailViewModel @Inject constructor(
    private val getShoeDetailUseCase: GetShoeDetailUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoeDetailContact())
    val uiState: StateFlow<ShoeDetailContact> get() = _uiState

    fun initShoeDetail(id: String) {
        flow {
            emit(getShoeDetailUseCase.invoke(id))
        }.onEach { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    _uiState.update { state ->
                        state.copy(shoeDetail = resource.data?.body())
                    }
                }

                Status.ERROR -> {
                    Log.e("ShoeDetailViewModel", "getDataShoe: Error ${resource.message}")
                }

                else -> {}
            }
        }.onStart {
            _uiState.update { it.copy(isLoading = true) }
        }.onCompletion {
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    fun handleCountShoe(type: Int) {
        viewModelScope.launch {
            val countShoe = when (type) {
                REDUCE -> {
                    if (uiState.value.countShoe > 0) {
                        uiState.value.countShoe.minus(1)
                    } else uiState.value.countShoe
                }

                PLUS -> {
                    if (uiState.value.countShoe < (uiState.value.sizeStore ?: 0) &&
                        uiState.value.countShoe < MAX_SHOE
                    ) {
                        uiState.value.countShoe.plus(1)
                    } else uiState.value.countShoe
                }

                else -> uiState.value.countShoe
            }
            _uiState.update { it.copy(countShoe = countShoe) }
        }
    }

    fun updateCount(count: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(countShoe = count) }
        }
    }

    fun handleEditTextCount() {
        viewModelScope.launch {
            val countShoe = if (uiState.value.countShoe > (uiState.value.sizeStore ?: 0))
                uiState.value.sizeStore
            else uiState.value.countShoe
            _uiState.update { it.copy(countShoe = countShoe ?: 0) }
        }
    }
}