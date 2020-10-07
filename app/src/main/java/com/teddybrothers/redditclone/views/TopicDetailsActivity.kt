package com.teddybrothers.redditclone.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.teddybrothers.redditclone.R
import com.teddybrothers.redditclone.databinding.ActivityTopicDetailsBinding
import com.teddybrothers.redditclone.models.Topic
import com.teddybrothers.redditclone.viewmodels.TopicViewModel
import com.teddybrothers.redditclone.views.MainActivity.Companion.PARAM_TOPIC
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject

class TopicDetailsActivity : AppCompatActivity() {

    lateinit var topic: Topic
    lateinit var binding: ActivityTopicDetailsBinding
    private val topicViewModel by inject<TopicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topic_details)

        intent.extras?.apply {
            topic = Gson().fromJson(getString(PARAM_TOPIC), Topic::class.java)
        }

        binding.topic = topic
        binding.topicViewModel = topicViewModel

        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = ""
        }
    }

    override fun onBackPressed() {
        val returnIntent = Intent().apply {
            putExtra(PARAM_TOPIC, Gson().toJson(binding.topic))
        }
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == android.R.id.home) // Press Back Icon
        {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}