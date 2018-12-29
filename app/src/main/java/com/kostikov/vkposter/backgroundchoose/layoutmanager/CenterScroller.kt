package com.kostikov.vkposter.backgroundchoose.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

/**
 * @author Kostikov Aleksey.
 */
class CenterScroller(context: Context) : LinearSmoothScroller(context) {

    override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
        return boxStart + (boxEnd - boxStart) / 2 - (viewStart + (viewEnd - viewStart) / 2)
    }

    override fun calculateTimeForScrolling(dx: Int): Int {
        return 150
    }
}