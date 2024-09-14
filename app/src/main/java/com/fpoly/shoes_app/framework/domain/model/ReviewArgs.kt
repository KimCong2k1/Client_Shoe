package com.fpoly.shoes_app.framework.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewArgs(
    val comments: List<Comment> = emptyList(),
    val rate: Float = 0F,
) : Parcelable