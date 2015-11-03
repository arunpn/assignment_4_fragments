package com.arunpn.twitterapp.service;

import com.arunpn.twitterapp.model.Followers;
import com.arunpn.twitterapp.model.PostTweetResponse;
import com.arunpn.twitterapp.model.Tweet;
import com.arunpn.twitterapp.model.TwitterUser;

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

    @GET("/statuses/user_timeline.json")
    public void getUserTimeLine(@Query("count") int count, @Query("page") int page, @Query("screen_name") String screen_name,Callback<List<Tweet>> callback);


    @GET("/statuses/mentions_timeline.json")
    public void getMentionsTimeLine(@Query("count") int count, @Query("page") int page, Callback<List<Tweet>> callback);

    @GET("/users/show.json")
    public void getUser(@Query("screen_name") String screen_name, Callback<TwitterUser> callback);

    @GET("/friends/list.json")
    public void getFollowers(@Query("screen_name") String screen_name, Callback<Followers> callback);


    @POST("/statuses/retweet/{tweetId}.json")
    public void postReTweet(@Path("tweetId") String tweetId, Callback<List<Tweet>> callback);

    @GET("/statuses/show/{tweetId}.json")
    public void getTweet(@Path("tweetId") String tweetId, Callback<List<Tweet>> callback);

}
