package com.fpoly.shoes_app.framework.domain.model.setUp

import android.os.Parcelable
import com.fpoly.shoes_app.framework.domain.model.forgotMail.BaseErrResponse
import com.fpoly.shoes_app.framework.domain.model.user.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SetUpAccount(
    @SerializedName("imageAccount") val imageAccount: String,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("birthDay") val birthDay: String,
    @SerializedName("grender") val grender: String,
): Parcelable
class SetUpAccountResponse(
    @SerializedName("user") val user: User?,
    success: Boolean,
    message: String?
) : BaseErrResponse(success, message)
