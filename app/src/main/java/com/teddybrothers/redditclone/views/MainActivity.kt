package com.teddybrothers.redditclone.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.teddybrothers.redditclone.R
import com.teddybrothers.redditclone.adapters.RecyclerViewListener
import com.teddybrothers.redditclone.adapters.RecyclerviewAdapter
import com.teddybrothers.redditclone.models.Topic
import com.teddybrothers.redditclone.viewmodels.TopicViewModel
import com.teddybrothers.redditclone.views.CreateTopicActivity.Companion.REQUEST_CODE_CREATE_TOPIC
import com.teddybrothers.redditclone.views.CreateTopicActivity.Companion.REQUEST_CODE_DETAIL_TOPIC
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), RecyclerViewListener, View.OnClickListener {

    companion object {
        const val PARAM_TOPIC = "topic"
    }

    private lateinit var recyclerviewAdapter: RecyclerviewAdapter
    private val topicViewModel by inject<TopicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setupList()
    }

    private fun init() {
        //init Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Reddit Clone"
        }

        //init button click
        createTopic.setOnClickListener(this)

        //init RecyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerviewAdapter = RecyclerviewAdapter(this)
        recyclerview.adapter = recyclerviewAdapter
        recyclerview.layoutManager = linearLayoutManager
    }

    private fun setupList() {
        val topicList = topicViewModel.topicList
        //Always return a list of top 20 topics sort descending based on voteCount
        val sortedList = topicList.sortedByDescending { it.voteCount }.take(20)

        recyclerviewAdapter.clear()
        recyclerviewAdapter.addItems(sortedList)
        recyclerviewAdapter.addViewModel(topicViewModel)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CREATE_TOPIC || requestCode == REQUEST_CODE_DETAIL_TOPIC) {
            setupList()
        }
    }

    override fun onTopicClickListener(item: Any, position: Int) {
        if (item is Topic) {
            startActivityForResult(Intent(this, TopicDetailsActivity::class.java).apply {
                putExtra(PARAM_TOPIC, Gson().toJson(item))
            }, REQUEST_CODE_DETAIL_TOPIC)
        }
    }

    override fun onClick(v: View?) {
        val id = v?.id
        if (id == R.id.createTopic) {
            startActivityForResult(
                Intent(this, CreateTopicActivity::class.java),
                REQUEST_CODE_CREATE_TOPIC
            )
        }
    }
}