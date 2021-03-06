package com.kostikov.vkposter.backgroundchoose.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * @author Kostikov Aleksey.
 */
class SquareFrameLayout

    @JvmOverloads
    constructor(context: Context,
                attrs: AttributeSet? = null,
                defStyleAttr: Int = 0,
                defStyleRes: Int = 0): FrameLayout(context, attrs, defStyleAttr)  {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}