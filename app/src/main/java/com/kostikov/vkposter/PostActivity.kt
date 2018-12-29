package com.kostikov.vkposter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.kostikov.vkposter.backgroundchoose.adapter.BackgroundAdapter
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        initBottomBackgroundChooseWindow()
    }


    private fun initBottomBackgroundChooseWindow() {

        postBackgroundRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@PostActivity).apply { orientation = HORIZONTAL }
            adapter = BackgroundAdapter()
        }
    }
}
