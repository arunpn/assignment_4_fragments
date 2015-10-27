package com.arunpn.twitterapp.service;

import com.arunpn.twitterapp.model.PostTweetResponse;
import com.arunpn.twitterapp.model.Tweet;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by a1nagar on 10/25/15.
 */
public interface TwitterApi {
    @POST("/statuses/update.json")
    void postTweet(@Query("status") String tweet, @Body String body, Callback<PostTweetResponse> callback);

    @GET("/statuses/home_timeline.json")
    public void getHomeTimeLine(@Query("count") int count, @Query("page") int page, Callback<List<Tweet>> callback);

    @GET("/statuses/retweets/{tweetId}.json")
    public void getReTweets(@Path("tweetId") String tweetId, Callback<List<Tweet>> callback);

    @GET("/statuses/show/{tweetId}.json")
    public void getTweet(@Path("tweetId") String tweetId, Callback<List<Tweet>> callback);

}
