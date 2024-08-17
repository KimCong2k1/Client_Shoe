package com.fpoly.shoes_app.utility

import android.content.Context
import com.fpoly.shoes_app.framework.domain.model.history.HistoryShoe


fun pxToDp(context: Context, number: Int): Int {
    return (number / context.resources.displayMetrics.density).toInt()
}

fun dpToPx(context: Context, number: Int): Int {
    return (number * context.resources.displayMetrics.density).toInt()
}
