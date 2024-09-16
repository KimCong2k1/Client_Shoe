package com.fpoly.shoes_app.framework.presentation.ui.checkout.address

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpoly.shoes_app.framework.domain.usecase.GetAddressUseCase
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
class AddressCheckoutViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase,
    private val sharedPreferences: SharedPreferencesManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddressCheckoutContract())
    val uiState: StateFlow<AddressCheckoutContract> get() = _uiState

    private val args = AddressCheckoutFragmentArgs.fromSavedStateHandle(savedStateHandle).args

    init {
        getAddress()
    }

    fun getAddress() {
        flow {
            emit(getAddressUseCase.invoke(sharedPreferences.getIdUser()))
        }.onEach { resource ->
            when (resource.status) {
                Status.SUCCESS -> _uiState.update {
                    it.copy(
                        address = resource.data,
                        isSelected = args,
                    )
                }

                Status.ERROR -> Log.e(
                    "CartViewModel", "getDataCart: Error ${resource.message}"
                )

                else -> {}
            }
        }.onStart {
            _uiState.update { it.copy(isLoading = true) }
        }.onCompletion {
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }
}