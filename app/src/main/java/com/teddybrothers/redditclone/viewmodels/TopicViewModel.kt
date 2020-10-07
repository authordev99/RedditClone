package com.teddybrothers.redditclone.viewmodels

import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.teddybrothers.redditclone.R
import com.teddybrothers.redditclone.models.Topic
import com.teddybrothers.redditclone.repository.SessionManager

class TopicViewModel(private val sessionManager: SessionManager) : ViewModel() {

    val topicList: ArrayList<Topic>
        get() = sessionManager.getTopicList

    fun createTopic(topic: Topic) {
        val list = topicList
        list.add(topic)
        sessionManager.saveTopic(list)
    }

    private fun updateTopic(topic: Topic, position: Int) {
        val list = topicList
        list[position] = topic
        sessionManager.saveTopic(list)
    }

    fun onVoteClick(
        voteUp: ImageButton,
        voteCountTextView: TextView,
        topic: Topic,
        voteDown: ImageButton,
        isVoteUp: Boolean
    ) {
        val list = topicList
        val oldTopic = list.find { it.id == topic.id }
        val position = list.indexOf(oldTopic)
        val context = voteUp.context
        var topicVoteCount = topic.voteCount
        if (isVoteUp) topicVoteCount++ else topicVoteCount--

        //Change resource (drawable and color)
        val voteUpDrawable =
            if (isVoteUp) R.drawable.ic_vote_up_active else R.drawable.ic_vote_up_inactive
        val voteDownDrawable =
            if (isVoteUp) R.drawable.ic_vote_down_inactive else R.drawable.ic_vote_down_active
        val voteCountColor =
            if (isVoteUp) R.color.colorVoteUp else R.color.colorVoteDown

        voteUp.setImageResource(voteUpDrawable)
        voteDown.setImageResource(voteDownDrawable)
        voteCountTextView.setTextColor(ContextCompat.getColor(context, voteCountColor))
        voteCountTextView.text = topicVoteCount.toString()

        //update topic models based on voteUp or voteDown
        topic.apply {
            isVoteDown = !isVoteUp
            this.isVoteUp = isVoteUp
            voteCount = topicVoteCount
        }

        //save to sharedPreference
        updateTopic(topic, position)
    }
}