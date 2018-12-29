package com.kostikov.vkposter.utils

import android.content.Context

/**
 * @author Kostikov Aleksey.
 */

fun Int.dp2px(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()

fun Float.dp2px(context: Context): Float = (this * context.resources.displayMetrics.density)

fun Int.px2dp(context: Context): Int = (this / context.resources.displayMetrics.density).toInt()