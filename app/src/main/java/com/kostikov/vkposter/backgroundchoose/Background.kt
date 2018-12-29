package com.kostikov.vkposter.backgroundchoose

import com.kostikov.vkposter.R
import java.io.File
import java.io.Serializable

/**
 * @author Kostikov Aleksey.
 */

data class Background(val type: BackgroundType = BackgroundType.COLORED,
                      val colorDrawableResId: Int? = R.drawable.background_white_full,
                      val imageFile: File? = null): Serializable

enum class BackgroundType {COLORED, BEACH, STARS, IMAGE }