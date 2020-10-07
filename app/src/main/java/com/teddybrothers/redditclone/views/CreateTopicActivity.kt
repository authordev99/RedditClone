package com.teddybrothers.redditclone.views

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.teddybrothers.redditclone.R
import com.teddybrothers.redditclone.models.Topic
import com.teddybrothers.redditclone.viewmodels.TopicViewModel
import kotlinx.android.synthetic.main.activity_create_topic.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject

class CreateTopicActivity : AppCompatActivity(), View.OnClickListener {

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
            title = "Text Post"
        }

        post.setOnClickListener(this)

        topicDescription.doOnTextChanged { text, _, _, _ ->
            topicDescriptionLength.text = (255 - text!!.length).toString()
        }

    }

    override fun onClick(v: View?) {
        val itemId = v?.id
        if (itemId == R.id.post) {
            val topicTitle = topicTitle.text.toString()
            val topicDescription = topicDescription.text.toString()
            val topic = Topic().apply {
                title = topicTitle
                description = topicDescription
            }
            topicViewModel.createTopic(topic)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}