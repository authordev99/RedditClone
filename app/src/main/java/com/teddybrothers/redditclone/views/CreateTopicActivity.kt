package com.teddybrothers.redditclone.views

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.teddybrothers.redditclone.R
import com.teddybrothers.redditclone.models.Topic
import com.teddybrothers.redditclone.viewmodels.TopicViewModel
import kotlinx.android.synthetic.main.activity_create_topic.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject


class CreateTopicActivity : AppCompatActivity() {

    private val topicViewModel by inject<TopicViewModel>()

    companion object {
        const val REQUEST_CODE_CREATE_TOPIC = 1000
        const val REQUEST_CODE_DETAIL_TOPIC = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_topic)

        init()
    }


    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = "Text Post"
        }

        topicDescription.doOnTextChanged { text, _, _, _ ->
            topicDescriptionLength.text = (255 - text!!.length).toString()
        }

    }

    private fun postTopic() {
        val topicTitle = topicTitle.text.toString()
        val topicDescription = topicDescription.text.toString()
        if (topicTitle.isNotEmpty()) {
            val topic = Topic().apply {
                title = topicTitle
                description = topicDescription
            }
            topicViewModel.createTopic(topic)
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Enter your title", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_post, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == android.R.id.home) // Press Back Icon
        {
            finish()
        } else if (itemId == R.id.post) {
            postTopic()
        }
        return super.onOptionsItemSelected(item)
    }
}