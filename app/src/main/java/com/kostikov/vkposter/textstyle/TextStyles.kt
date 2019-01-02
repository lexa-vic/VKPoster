package com.kostikov.vkposter.textstyle

import androidx.annotation.ColorRes
import com.kostikov.vkposter.R

/**
 * @author Kostikov Aleksey.
 */
enum class TextStyle(@ColorRes val textColor: Int, @ColorRes val backgroundColor: Int) {

    WHITE(R.color.white, R.color.transparent),
    WHITE_WITH_BACKGROUND(R.color.white, R.color.whiteTransparent),
    BLACK_WITH_BACKGROUND(R.color.black, R.color.white),
}

val textStyleList = listOf<TextStyle>(TextStyle.BLACK_WITH_BACKGROUND, TextStyle.WHITE, TextStyle.WHITE_WITH_BACKGROUND)
