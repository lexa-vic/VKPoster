package com.kostikov.vkposter.backgroundchoose

import com.kostikov.vkposter.R
import java.io.Serializable

/**
 * @author Kostikov Aleksey.
 */

sealed class Background(val colorDrawableResId: Int? = R.drawable.background_white_full,
                        val listColorDrawableResId: Int? = R.drawable.background_white): Serializable {
}

class Color(colorDrawableResId: Int?, listColorDrawableResId: Int?): Background(colorDrawableResId, listColorDrawableResId)

class Beach(colorDrawableResId: Int?,
            val topDrawableResId: Int?,
            val bodyDrawableResId: Int?,
            val bottomDrawableResId: Int?): Background(colorDrawableResId)

class Stars(colorDrawableResId: Int?, val bodyDrawableResId: Int?): Background(colorDrawableResId)

class Image(colorDrawableResId: Int?): Background(colorDrawableResId)