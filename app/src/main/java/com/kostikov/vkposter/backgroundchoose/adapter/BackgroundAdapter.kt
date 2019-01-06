package com.kostikov.vkposter.backgroundchoose.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kostikov.vkposter.R
import com.kostikov.vkposter.backgroundchoose.Background
import com.kostikov.vkposter.backgroundchoose.Beach
import com.kostikov.vkposter.backgroundchoose.Image
import com.kostikov.vkposter.backgroundchoose.Stars
import com.kostikov.vkposter.utils.BackgroundGlideImageTarget


/**
 * @author Kostikov Aleksey.
 */

typealias ClickHandler = (Int) -> Unit

class BackgroundAdapter(private val data: List<Background> = backgroundData,
                        private val onItemClick: ClickHandler? = null):
                                    RecyclerView.Adapter<BackgroundAdapter.BackgroundViewHolder>(), BackgroundSelect {

    private var selectedItemPosition: Int = 0
    private var recyclerView: RecyclerView? = null

    init { setHasStableIds(true) }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackgroundAdapter.BackgroundViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.background_list_item, parent, false)

        return BackgroundAdapter.BackgroundViewHolder(view)
    }

    override fun getItemId(position: Int) = data[position].hashCode().toLong()

    override fun getItemCount()= data.size

    override fun onBindViewHolder(holder: BackgroundAdapter.BackgroundViewHolder, position: Int) {
        val item = data[position]

        when(item) {
            is Image,
            is Beach,
            is Stars -> {
                var requestOptions = RequestOptions()
                requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(10))
                Glide.with(holder.itemView.getContext())
                    .load(item.colorDrawableResId!!)
                    .apply(requestOptions)
                    .into(BackgroundGlideImageTarget(holder.backgroundView))
            }
            else -> {
                holder.backgroundView.setBackgroundResource(item.listColorDrawableResId!!)
            }
        }

        if(position == selectedItemPosition) {
            holder.backgroundView.setImageResource(R.drawable.drawable_list_selection)
        } else {
            holder.backgroundView.setImageResource(android.R.color.transparent)
        }

        holder.backgroundView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }

    override fun setSelectedItem(selectedPosition: Int) {
        selectedItemPosition = selectedPosition
        recyclerView?.smoothScrollToPosition(selectedItemPosition)

        notifyDataSetChanged()
    }

    class BackgroundViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val backgroundView: ImageView = itemView.findViewById(R.id.backgroundListImage)
    }
}

interface BackgroundSelect {
    fun setSelectedItem(selectedPosition: Int)
}