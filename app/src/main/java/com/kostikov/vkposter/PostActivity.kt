package com.kostikov.vkposter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.kostikov.vkposter.backgroundchoose.*
import com.kostikov.vkposter.backgroundchoose.adapter.BackgroundAdapter
import com.kostikov.vkposter.backgroundchoose.adapter.BackgroundSelect
import com.kostikov.vkposter.backgroundchoose.adapter.backgroundData
import com.kostikov.vkposter.backgroundchoose.layoutmanager.CenterLinearLayoutManager
import com.kostikov.vkposter.utils.px2dp
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        mainConstraintLayout.addOnLayoutChangeListener(LayoutWatcher())
        initBottomBackgroundChooseWindow()
    }

    private fun initBottomBackgroundChooseWindow() {

        postBackgroundRecyclerView.apply {
            layoutManager = CenterLinearLayoutManager(this@PostActivity).apply { orientation = HORIZONTAL }
            adapter = BackgroundAdapter(){
                onSelectBackgroundTypeHandle(it)
            }
        }
    }

    private fun onSelectBackgroundTypeHandle(position: Int) {

        val background = backgroundData[position]
        postTopBackgroundImage.visibility = View.GONE
        postBottomBackgroundImage.visibility = View.GONE

        when(background) {
            is Color -> postBackgroundImage.setBackgroundResource(background.colorDrawableResId!!)
            is Beach -> {

                //postTopBackgroundImage.setBackgroundResource(background.topDrawableResId!!)
                postTopBackgroundImage.visibility = View.VISIBLE
                postBackgroundImage.setBackgroundResource(background.bodyDrawableResId!!)
                postBottomBackgroundImage.visibility = View.VISIBLE
            }
            is Stars -> postBackgroundImage.setBackgroundResource(background.bodyDrawableResId!!)
            is Image -> {}
        }

        (postBackgroundRecyclerView.adapter as BackgroundSelect).setSelectedItem(position)
    }

    inner class LayoutWatcher: View.OnLayoutChangeListener {

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
    }
}
