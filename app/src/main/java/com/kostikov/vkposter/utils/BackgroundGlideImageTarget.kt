package com.kostikov.vkposter.utils

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import com.bumptech.glide.request.target.ImageViewTarget

/**
 * @author Kostikov Aleksey.
 */
class BackgroundGlideImageTarget(view: ImageView?): ImageViewTarget<Drawable>(view) {

    override fun setResource(resource: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = resource
        } else {
            view.setBackgroundDrawable(resource)
        }
    }
}