package com.fpoly.shoes_app.utility

import android.widget.ImageView
import coil.load
import com.fpoly.shoes_app.R
import java.math.BigInteger
import java.security.MessageDigest
import java.text.NumberFormat
import java.util.Locale

fun ImageView.loadImage(imgResource: Int? = null) {
    this.load(imgResource) {
        placeholder(R.color.primary_white)
        error(R.color.primary_white)
    }
}

fun ImageView.loadImage(imageUrl: String? = null) {
    this.load(imageUrl) {
        placeholder(R.color.primary_white)
        error(R.color.primary_white)
    }
}

fun Int.formatPriceShoe(): String {
    return this.toString() + "đ"
}

fun Int.formatSoldShoe(): String {
    return "Đã bán $this"
}

fun String.toMD5(): String {
    val bytes = this.toByteArray()
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(bytes)
    return digest.joinToString("") { "%02x".format(it) }
}

fun String.formatToVND(): String {
    val number = this.toLongOrNull() ?: return "Invalid number"
    val format = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
    return format.format(number)
}