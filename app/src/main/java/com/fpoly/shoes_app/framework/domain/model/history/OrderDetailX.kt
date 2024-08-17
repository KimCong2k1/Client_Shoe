package com.fpoly.shoes_app.framework.domain.model.history

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class OrderDetailX(
    val _id: String,
    val amount: Int,
    val codeColor: String,
    val name: String,
    val price: Int,
    val quantity: Int,
    val size: String,
    val textColor: String,
    val thumbnail: String
) : Parcelable