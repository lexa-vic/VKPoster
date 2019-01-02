package com.kostikov.vkposter.utils

import android.content.Context
import android.view.View

/**
 * @author Kostikov Aleksey.
 */

fun Int.dp2px(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()

fun Float.dp2px(context: Context): Float = (this * context.resources.displayMetrics.density)

fun Int.px2dp(context: Context): Int = (this / context.resources.displayMetrics.density).toInt()

fun Float.px2dp(context: Context): Float = (this / context.resources.displayMetrics.density).toFloat()

fun View.show(show: Boolean) {
    if (show) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}
