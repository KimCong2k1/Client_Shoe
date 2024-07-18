package com.fpoly.shoes_app.framework.repository

import com.fpoly.shoes_app.framework.data.dataremove.api.postInterface.SetUpInterface
import com.fpoly.shoes_app.framework.data.dataremove.api.postInterface.SignUpInterface
import com.fpoly.shoes_app.framework.domain.model.setUp.SetUpAccount
import com.fpoly.shoes_app.framework.domain.model.signUp.SignUp
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class SetUpAccountRepository @Inject constructor(
    private val setUpApi: SetUpInterface
) {
    suspend fun signUp(id: String, setUp: SetUpAccount, imageFile: File?) = setUpApi.setUpAccount(
        id,
        imageFile?.let {
            val requestFile = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("imageAccount", it.name, requestFile)
        },
        RequestBody.create("text/plain".toMediaTypeOrNull(), setUp.fullName),
        RequestBody.create("text/plain".toMediaTypeOrNull(), setUp.nameAccount),
        RequestBody.create("text/plain".toMediaTypeOrNull(), setUp.phoneNumber),
        RequestBody.create("text/plain".toMediaTypeOrNull(), setUp.birthDay),
        RequestBody.create("text/plain".toMediaTypeOrNull(), setUp.grender)
    )
}