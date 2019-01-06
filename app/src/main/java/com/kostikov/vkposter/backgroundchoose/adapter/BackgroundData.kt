package com.kostikov.vkposter.backgroundchoose.adapter

import com.kostikov.vkposter.R
import com.kostikov.vkposter.backgroundchoose.Beach
import com.kostikov.vkposter.backgroundchoose.Color
import com.kostikov.vkposter.backgroundchoose.Image
import com.kostikov.vkposter.backgroundchoose.Stars

/**
 * @author Kostikov Aleksey.
 */
var backgroundData = listOf(
        Color(colorDrawableResId = R.drawable.background_white_full, listColorDrawableResId = R.drawable.background_white),
        Color(colorDrawableResId = R.drawable.background_blue_full, listColorDrawableResId = R.drawable.background_blue),
        Color(colorDrawableResId = R.drawable.background_green_full, listColorDrawableResId = R.drawable.background_green),
        Color(colorDrawableResId = R.drawable.background_orange_full, listColorDrawableResId = R.drawable.background_orange),
        Color(colorDrawableResId = R.drawable.background_red_full, listColorDrawableResId = R.drawable.background_red),
        Color(colorDrawableResId = R.drawable.background_violet_full, listColorDrawableResId = R.drawable.background_violet),
        Beach(colorDrawableResId = R.drawable.thumb_beach,
                topDrawableResId = R.drawable.bg_beach_top,
                bodyDrawableResId = R.drawable.bg_beach_center,
                bottomDrawableResId = R.drawable.bg_beach_bottom),
        Stars(colorDrawableResId = R.drawable.thumb_stars, bodyDrawableResId = R.drawable.bg_stars_center),
        Image(colorDrawableResId = R.drawable.thumb_add)
)