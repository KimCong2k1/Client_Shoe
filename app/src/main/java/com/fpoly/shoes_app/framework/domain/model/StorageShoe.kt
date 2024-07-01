package com.fpoly.shoes_app.framework.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StorageShoe(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("importQuanlity")
    val quantity: Int? = 0,
    @SerializedName("soldQuanlity")
    val sold: Int? = 0,
) : Parcelable