package com.fpoly.shoes_app.framework.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shoes(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("shoeId")
    val shoeId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Int? = 0,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
//    @SerializedName("rate")
//    var rate: Float? = 0F,
//    @SerializedName("sold")
//    var sold: Int? = 0,
//    @SerializedName("gender")
//    var gender: Int? = 0, // 0: All, 1: Male, 2: Female
//    @SerializedName("brandShoe")
//    val category: String? = null,
    @SerializedName("shoeDetail")
    var shoeDetail: ShoeDetail? = null,
//    @SerializedName("status")
//    val status: Int? = 0 // 0: Inactive, 1: Active, 2: Sold
) : Parcelable

@Parcelize
data class ShoeDetail(
    @SerializedName("imageShoe")
    var imageShoe: ImageShoe? = null,
    @SerializedName("sizeShoe")
    val sizeShoe: List<SizeShoe>? = emptyList(),
//    @SerializedName("colorShoe")
//    val colorShoe: List<ColorShoe>? = emptyList(),
) : Parcelable

@Parcelize
data class ImageShoe(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("imageShoe")
    var imageShoe: List<String>? = emptyList(),
) : Parcelable

@Parcelize
data class SizeShoe(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("size")
    val size: String? = null,
    @SerializedName("quantity")
    val quantity: Int? = 0
) : Parcelable

@Parcelize
data class ColorShoe(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("textColor")
    val textColor: String? = null,
    @SerializedName("codeColor")
    val codeColor: String? = null,
//    @SerializedName("quantity")
//    val quantity: Int? = 0
) : Parcelable