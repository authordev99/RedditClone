package com.teddybrothers.redditclone.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teddybrothers.redditclone.R
import com.teddybrothers.redditclone.databinding.ListItemTopicBinding
import com.teddybrothers.redditclone.models.Topic
import com.teddybrothers.redditclone.viewmodels.TopicViewModel


class RecyclerviewAdapter(private val listener: RecyclerViewListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var topicList = ArrayList<Topic>()
    private lateinit var topicViewModel : TopicViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_topic,
                parent,
                false
            ),
            listener
        )
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

    fun addItem(topic : Topic) {
        topic.let {
            this.topicList.add(topic)
            notifyDataSetChanged()
        }
    }

    fun updateItem(topic : Topic) {
        val oldTopic = topicList.find { it.id == topic.id }
        val oldTopicPosition = topicList.indexOf(oldTopic)
        topicList[oldTopicPosition] = topic
        notifyItemChanged(oldTopicPosition)
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
}