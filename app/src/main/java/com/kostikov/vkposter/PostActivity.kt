package com.kostikov.vkposter

import android.Manifest
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kostikov.vkposter.backgroundchoose.Beach
import com.kostikov.vkposter.backgroundchoose.Color
import com.kostikov.vkposter.backgroundchoose.Image
import com.kostikov.vkposter.backgroundchoose.Stars
import com.kostikov.vkposter.backgroundchoose.adapter.BackgroundAdapter
import com.kostikov.vkposter.backgroundchoose.adapter.BackgroundSelect
import com.kostikov.vkposter.backgroundchoose.adapter.backgroundData
import com.kostikov.vkposter.backgroundchoose.layoutmanager.CenterLinearLayoutManager
import com.mlsdev.rximagepicker.RxImagePicker
import com.mlsdev.rximagepicker.Sources
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_post.*


class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //mainConstraintLayout.addOnLayoutChangeListener(LayoutWatcher())
        initBottomBackgroundChooseWindow()
    }

    private fun initBottomBackgroundChooseWindow() {

        postBackgroundRecyclerView.apply {
            layoutManager = CenterLinearLayoutManager(this@PostActivity).apply { orientation = HORIZONTAL }
            adapter = BackgroundAdapter {
                onSelectBackgroundTypeHandle(it)
            }
        }
    }

    private fun onSelectBackgroundTypeHandle(position: Int) {
        val background = backgroundData[position]

        when(background) {
            is Color -> {
                setHeaderAndFooterVisibility(View.GONE)

                postBackgroundImage.setImageDrawable(resources.getDrawable(background.colorDrawableResId!!))
            }
            is Beach -> {
                setHeaderAndFooterVisibility(View.VISIBLE)

                postBackgroundImage.setImageDrawable(null)
                postBackgroundImage.setBackgroundResource(background.bodyDrawableResId!!)
            }
            is Stars -> {
                setHeaderAndFooterVisibility(View.GONE)

                postBackgroundImage.setImageDrawable(null)
                postBackgroundImage.setBackgroundResource(background.bodyDrawableResId!!)
            }
            is Image -> {
                setHeaderAndFooterVisibility(View.GONE)

                RxPermissions(this)
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .filter { it }
                    .flatMap { RxImagePicker.with(this).requestImage(Sources.GALLERY) }
                    .subscribe({
                        setBackgroundFromUri(it)
                    }, {
                        it.printStackTrace()
                    })

            }
        }

        (postBackgroundRecyclerView.adapter as BackgroundSelect).setSelectedItem(position)
    }

    private fun setBackgroundFromUri(uri: Uri) {
        val screenSize = Point()
        windowManager.defaultDisplay.getSize(screenSize)

        Glide.with(this)
            .load(uri)
            .apply(RequestOptions.centerCropTransform())
            .into(postBackgroundImage)
    }

    private fun setHeaderAndFooterVisibility(visibility: Int){

        postTopBackgroundImage.visibility = visibility
        postBottomBackgroundImage.visibility = visibility
    }


   /* inner class LayoutWatcher: View.OnLayoutChangeListener {

        override fun onLayoutChange(
            v: View?,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {

        }
    }*/
}
