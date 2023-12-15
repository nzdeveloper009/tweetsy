package com.android.tweetsy.repository

import com.android.tweetsy.api.TweetsyAPI
import com.android.tweetsy.models.TweetListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TweetRepository @Inject constructor(private val tweetsyAPI: TweetsyAPI) {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>>
        get() = _categories

    private val _tweets = MutableStateFlow<List<TweetListItem>>(emptyList())
    val tweets: StateFlow<List<TweetListItem>>
        get() = _tweets


    suspend fun getCategories() {
        val result = tweetsyAPI.getCategories()
        if (result.isSuccessful && result.body() != null) {
            _categories.emit(result.body()!!)
        }
    }

    suspend fun getTweets(category:String) {
        val result = tweetsyAPI.getTweets("tweets[?(@.category==\"$category\")]")
        if (result.isSuccessful && result.body() != null) {
            _tweets.emit(result.body()!!)
        }
    }




}