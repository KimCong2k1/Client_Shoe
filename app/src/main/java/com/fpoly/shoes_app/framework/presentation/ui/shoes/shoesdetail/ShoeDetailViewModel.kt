package com.fpoly.shoes_app.framework.presentation.ui.shoes.shoesdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpoly.shoes_app.framework.domain.usecase.GetShoeDetailUseCase
import com.fpoly.shoes_app.utility.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
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
        }.launchIn(viewModelScope)
    }
}