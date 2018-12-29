package com.kostikov.vkposter.backgroundchoose.adapter

import com.kostikov.vkposter.R
import com.kostikov.vkposter.backgroundchoose.Background
import com.kostikov.vkposter.backgroundchoose.BackgroundType

/**
 * @author Kostikov Aleksey.
 */
var backgroundData = listOf(
    Background(type = BackgroundType.COLORED, colorDrawableResId = R.drawable.background_white_full),
    Background(type = BackgroundType.COLORED, colorDrawableResId = R.drawable.background_blue_full),
    Background(type = BackgroundType.COLORED, colorDrawableResId = R.drawable.background_green_full),
    Background(type = BackgroundType.COLORED, colorDrawableResId = R.drawable.background_orange_full),
    Background(type = BackgroundType.COLORED, colorDrawableResId = R.drawable.background_red_full),
    Background(type = BackgroundType.COLORED, colorDrawableResId = R.drawable.background_violet_full),
    Background(type = BackgroundType.BEACH, colorDrawableResId = R.drawable.thumb_beach),
    Background(type = BackgroundType.STARS, colorDrawableResId = R.drawable.thumb_stars),
    Background(type = BackgroundType.IMAGE, colorDrawableResId = R.drawable.thumb_add)
)