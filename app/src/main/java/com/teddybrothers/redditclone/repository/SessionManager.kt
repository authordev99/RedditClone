package com.teddybrothers.redditclone.repository

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.teddybrothers.redditclone.models.Topic
import java.util.ArrayList

class SessionManager(context: Context) {

    companion object {
        const val KEY_LIST_TOPIC = "list_topic"
        const val PRIVATE_MODE = 0
        const val PREF_NAME = "reddit"
    }

    private var editor: Editor
    private var pref: SharedPreferences
    private var topicList : ArrayList<Topic>

    val getTopicList: ArrayList<Topic>
        get() = Gson().fromJson<ArrayList<Topic>>(pref.getString(KEY_LIST_TOPIC, Gson().toJson(ArrayList<Any>())), object : TypeToken<ArrayList<Topic>>() {
        }.type)

    init {
        pref = context.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )
        editor = pref.edit()
        topicList = getTopicList
    }

    fun saveTopic(topicList: ArrayList<Topic>) {
        editor.putString(KEY_LIST_TOPIC, Gson().toJson(topicList))
        editor.commit()
    }

}