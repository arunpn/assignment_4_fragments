package com.arunpn.twitterapp.service;

import com.arunpn.twitterapp.model.PostTweetResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by a1nagar on 10/25/15.
 */
public interface TwitterApi {
    @POST("/statuses/update.json")
    void postTweet(@Query("status") String tweet, @Body String body, Callback<PostTweetResponse> callback);

//    @GET("/statuses/home_timeline.json")
//    void getHomeTimeLine();
//
//    @GET("/statuses/user_timeline.json")
//    void getUserTimeline(
//            @Query("screen_name") String screenName,
//            @Query("count") int count,
//            Callback<>
//    );
}
