package com.kostikov.vkposter.backgroundchoose

import com.kostikov.vkposter.R
import java.io.File
import java.io.Serializable

/**
 * @author Kostikov Aleksey.
 */

sealed class Background(val colorDrawableResId: Int? = R.drawable.background_white_full): Serializable {
}

class Color(colorDrawableResId: Int?): Background(colorDrawableResId)
class Beach(colorDrawableResId: Int?): Background(colorDrawableResId)
class Stars(colorDrawableResId: Int?): Background(colorDrawableResId)
class Image(colorDrawableResId: Int?, val imageFile: File? = null): Background(colorDrawableResId)