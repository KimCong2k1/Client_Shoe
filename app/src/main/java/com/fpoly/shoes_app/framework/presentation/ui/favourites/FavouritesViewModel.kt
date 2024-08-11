package com.fpoly.shoes_app.framework.presentation.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpoly.shoes_app.framework.domain.model.favourite.Favourites
import com.fpoly.shoes_app.framework.repository.FavouritesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val _favourites = MutableLiveData<List<Favourites>?>()
    val favourites: LiveData<List<Favourites>?> get() = _favourites

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchFavourites(userId: String) {
        viewModelScope.launch {
            try {
                // Gọi phương thức getFavourites() để lấy Response chứa danh sách yêu thích
                val response = favouritesRepository.getFavourites(userId)

                // Kiểm tra nếu Response thành công và lấy dữ liệu từ Response
                if (response.isSuccessful) {
                    val favouritesList = response.body() // Lấy danh sách yêu thích từ Response
                    if (favouritesList != null) {
                        _favourites.postValue(favouritesList)
                    } else {
                        _error.postValue("Danh sách yêu thích trống")
                    }
                } else {
                    _error.postValue("Lỗi: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}