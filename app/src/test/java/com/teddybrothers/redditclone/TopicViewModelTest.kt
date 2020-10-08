package com.teddybrothers.redditclone

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.teddybrothers.redditclone.models.Topic
import com.teddybrothers.redditclone.utils.appModule
import com.teddybrothers.redditclone.viewmodels.TopicViewModel
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class TopicViewModelTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val topicViewModel: TopicViewModel by inject()
    private lateinit var context: Context
    private lateinit var mockTopic : Topic

    @Before
    fun before() {
        context = InstrumentationRegistry.getInstrumentation().context
        mockTopic = Topic()
        startKoin {
            androidContext(context)
            modules(appModule)
        }
    }

    @Test
    fun test_getTopicListFromRepository() {
        assertNotNull(topicViewModel.topicList)
        assertTrue(topicViewModel.topicList.isEmpty())
    }

    @Test
    fun test_createAndSaveTopic() {
        topicViewModel.createTopic(mockTopic)
        assertEquals( 1, topicViewModel.topicList.size)
        assertEquals( true, topicViewModel.topicList.isNotEmpty())
    }

    @Test
    fun test_updateTopic() {
        topicViewModel.createTopic(mockTopic)
        val newTopic = Topic().apply {
            voteCount = 1
        }
        topicViewModel.updateTopic(newTopic,0)
        assertNotSame("Checking oldTopic should be not same with newTopic", mockTopic.voteCount, topicViewModel.topicList[0].voteCount)
    }

    @After
    fun after() {
        stopKoin()
    }

}