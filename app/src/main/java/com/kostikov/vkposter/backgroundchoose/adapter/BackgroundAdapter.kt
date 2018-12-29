package com.kostikov.vkposter.backgroundchoose.adapter

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kostikov.vkposter.BuildConfig
import com.kostikov.vkposter.R
import com.kostikov.vkposter.backgroundchoose.BackgroundType
import com.kostikov.vkposter.utils.dp2px

/**
 * @author Kostikov Aleksey.
 */
class BackgroundAdapter: RecyclerView.Adapter<BackgroundAdapter.BackgroundViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackgroundAdapter.BackgroundViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.background_list_item, parent, false)

        return BackgroundAdapter.BackgroundViewHolder(view)
    }

    override fun getItemCount()= backgroundData.size

    override fun onBindViewHolder(holder: BackgroundAdapter.BackgroundViewHolder, position: Int) {
        val item = backgroundData[position]
        holder.backgroundView.setBackgroundResource(item.colorDrawableResId!!)
       /* when(item.type) {
            BackgroundType.COLORED -> {

                holder.backgroundView.setBackgroundResource(item.colorDrawableResId!!)
            }
            BackgroundType.BEACH -> {

                holder.backgroundView.setBackgroundResource(item.colorDrawableResId!!)
            }
            BackgroundType.STARS -> {
                holder.backgroundView.setBackgroundResource(item.colorDrawableResId!!)
            }
            BackgroundType.IMAGE -> {

            }
        }*/
    }

    class BackgroundViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val backgroundView: View = itemView.findViewById(R.id.background_list_item_image)
    }
}