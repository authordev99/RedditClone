package com.teddybrothers.redditclone.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teddybrothers.redditclone.R
import com.teddybrothers.redditclone.databinding.ListItemLoadingBinding
import com.teddybrothers.redditclone.databinding.ListItemTopicBinding
import com.teddybrothers.redditclone.models.Topic


class RecyclerviewAdapter(private val listener: RecyclerViewListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var topicList = ArrayList<Topic>()
    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1
    private var isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_NORMAL) {
            return MainViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item_topic,
                    parent,
                    false
                ),
                listener
            )
        } else {
            return ProgressViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item_loading,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == topicList.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    fun addLoading() {
        isLoaderVisible = true
        topicList.add(Topic())
        notifyItemInserted(topicList.size-1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position: Int = topicList.size -1
        topicList.removeAt(position)
        notifyItemRemoved(position)

    }

    fun clear() {
        topicList.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val Topic = this.topicList[position]
        if (holder is MainViewHolder) {
            holder.updateData(Topic)
        }
    }

    override fun getItemCount() : Int {
        return topicList.size
    }

    fun addItems(TopicList: List<Topic>?) {
        if (!TopicList.isNullOrEmpty()) {
            this.topicList.addAll(TopicList)
        }
        notifyDataSetChanged()
    }


    class MainViewHolder(val binding: ListItemTopicBinding, listener: RecyclerViewListener) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var topic: Topic

        init {
            itemView.setOnClickListener {
                listener.onClickListener(topic, layoutPosition)
            }
        }


        fun updateData(topic: Topic) {
            this.topic = topic
            binding.topic = topic
        }
    }

    class ProgressViewHolder(binding: ListItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)
}