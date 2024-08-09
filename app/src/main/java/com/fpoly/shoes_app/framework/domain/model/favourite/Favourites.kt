package com.fpoly.shoes_app.framework.domain.model.favourite

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favourites (
    @SerializedName("favouriteId")
    val favouriteId: String?,
    @SerializedName("shoeId")
    val shoeId: List<String>,
    @SerializedName("userId")
    val userId: String,
): Parcelable