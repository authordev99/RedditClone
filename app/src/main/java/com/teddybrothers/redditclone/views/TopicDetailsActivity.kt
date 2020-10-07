package com.teddybrothers.redditclone.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.teddybrothers.redditclone.R
import com.teddybrothers.redditclone.databinding.ActivityTopicDetailsBinding
import com.teddybrothers.redditclone.models.Topic
import com.teddybrothers.redditclone.viewmodels.TopicViewModel
import com.teddybrothers.redditclone.views.MainActivity.Companion.PARAM_TOPIC
import org.koin.android.ext.android.inject

class TopicDetailsActivity : AppCompatActivity() {

    lateinit var topic: Topic
    lateinit var binding: ActivityTopicDetailsBinding
    private val topicViewModel by inject<TopicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topic_details)

        intent.extras?.apply {
            topic = Gson().fromJson(getString(MainActivity.PARAM_TOPIC), Topic::class.java)
        }

        binding.topic = topic
        binding.topicViewModel = topicViewModel
    }

    override fun onBackPressed() {
        val returnIntent = Intent().apply {
            putExtra(PARAM_TOPIC, Gson().toJson(binding.topic))
        }
        setResult(Activity.RESULT_OK,returnIntent)
        finish()
    }
}