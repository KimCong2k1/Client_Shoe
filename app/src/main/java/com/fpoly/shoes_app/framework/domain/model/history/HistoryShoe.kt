package com.fpoly.shoes_app.framework.domain.model.history

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryShoe(
    val _id: String,
    val dateOrder: String,
    val name: String,
    val orderDetails: List<OrderDetailX>, // List of order details for this shoe
    val pay: String,
    val promo: Int,
    val status: String,
    val thumbnail: String,
    val total: Int,
    val totalPre: Int
) : Parcelable