package com.teddybrothers.redditclone.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teddybrothers.redditclone.R
import com.teddybrothers.redditclone.databinding.ListItemLoadingBinding
import com.teddybrothers.redditclone.databinding.ListItemTopicBinding
import com.teddybrothers.redditclone.models.Topic
import com.teddybrothers.redditclone.viewmodels.TopicViewModel


class RecyclerviewAdapter(private val listener: RecyclerViewListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var topicList = ArrayList<Topic>()
    private lateinit var topicViewModel : TopicViewModel
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
        val topic = this.topicList[position]
        if (holder is MainViewHolder) {
            holder.bindingData(topic,topicViewModel)
        }
    }

    override fun getItemCount() : Int {
        return topicList.size
    }

    fun addViewModel(topicViewModel: TopicViewModel) {
        this.topicViewModel = topicViewModel
    }

    fun addItems(topicList: List<Topic>?) {
        if (!topicList.isNullOrEmpty()) {
            this.topicList.addAll(topicList)
        }
        notifyDataSetChanged()
    }



    class MainViewHolder(private val binding: ListItemTopicBinding, listener: RecyclerViewListener) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var topicData: Topic

        init {
            itemView.setOnClickListener {
                listener.onTopicClickListener(topicData, layoutPosition)
            }
        }

        fun bindingData(topic: Topic,topicViewModel: TopicViewModel) {
            this.topicData = topic
            binding.topic = topicData
            binding.topicViewModel = topicViewModel
        }
    }

    class ProgressViewHolder(binding: ListItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)
}