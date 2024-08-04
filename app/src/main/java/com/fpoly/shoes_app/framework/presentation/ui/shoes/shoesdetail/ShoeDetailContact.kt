package com.fpoly.shoes_app.framework.presentation.ui.shoes.shoesdetail

import android.os.Parcelable
import com.fpoly.shoes_app.framework.domain.model.Shoes
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShoeDetailContact(
    val shoeDetail: Shoes? = null,
    val isLoading: Boolean? = false,
    val countShoe: Int = 0,
) : Parcelable {
    val sizeStore get() = shoeDetail?.storageShoe?.size
    val isButtonEnable get() = countShoe > 0
    val priceTotal get() = (shoeDetail?.price ?: 0) * countShoe
}