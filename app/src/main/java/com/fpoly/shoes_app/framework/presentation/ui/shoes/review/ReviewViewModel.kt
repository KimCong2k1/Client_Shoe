package com.fpoly.shoes_app.framework.presentation.ui.shoes.review

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> get() = _uiState

    private val args = ReviewFragmentArgs.fromSavedStateHandle(savedStateHandle).args

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(reviews = args.comments) }
        }
    }
}