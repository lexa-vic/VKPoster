package com.kostikov.vkposter.backgroundchoose.adapter

import android.util.SparseArray
import android.util.SparseLongArray
import com.kostikov.vkposter.R
import com.kostikov.vkposter.backgroundchoose.*

/**
 * @author Kostikov Aleksey.
 */
var backgroundData = listOf(
        Color(colorDrawableResId = R.drawable.background_white_full),
        Color( colorDrawableResId = R.drawable.background_blue_full),
        Color( colorDrawableResId = R.drawable.background_green_full),
        Color( colorDrawableResId = R.drawable.background_orange_full),
        Color(colorDrawableResId = R.drawable.background_red_full),
        Color( colorDrawableResId = R.drawable.background_violet_full),
        Beach( colorDrawableResId = R.drawable.thumb_beach,
                topDrawableResId = R.drawable.bg_beach_top,
                bodyDrawableResId = R.drawable.bg_beach_center,
                bottomDrawableResId = R.drawable.bg_beach_bottom),
        Stars(colorDrawableResId = R.drawable.thumb_stars, bodyDrawableResId = R.drawable.bg_stars_center),
        Image(colorDrawableResId = R.drawable.thumb_add)
)