package com.ai.weathernotifications.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ai.weathernotifications.R
import com.ai.weathernotifications.data.db.WeatherNotification
import com.ai.weathernotifications.databinding.FragmentItemBinding
import com.bumptech.glide.Glide

class WeatherNotificationRecyclerViewAdapter(
    private val onItemClick: (item: WeatherNotification) -> Unit
) : ListAdapter<WeatherNotification, WeatherNotificationRecyclerViewAdapter.WeatherNVH>(
    WeatherNotifComparator()
) {

    inner class WeatherNVH(private val itemBinding: FragmentItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(item: WeatherNotification) {
            with(itemBinding) {
                eventNameTv.text = item.name
                eventTimeTv.text = "${item.startDate} - ${item.endDate}"
                senderNameTv.text = item.source
                Glide.with(eventIv)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.ic_baseline_play_circle_outline_24)
                    .into(eventIv)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherNVH {

        return WeatherNVH(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherNVH, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class WeatherNotifComparator : DiffUtil.ItemCallback<WeatherNotification>() {
    override fun areItemsTheSame(
        oldItem: WeatherNotification,
        newItem: WeatherNotification
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: WeatherNotification,
        newItem: WeatherNotification
    ): Boolean = oldItem == newItem

}