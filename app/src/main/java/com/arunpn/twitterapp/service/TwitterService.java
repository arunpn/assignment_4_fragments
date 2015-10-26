package com.arunpn.twitterapp.service;

import com.arunpn.twitterapp.ConfigKeys;
import com.arunpn.twitterapp.model.PostTweetResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by a1nagar on 10/25/15.
 */
public class TwitterService {
    public static final String BASE_URL = "https://api.twitter.com/1.1";
    public static final String EMPTY_BODY = "{}";
    private TwitterApi mApi;

    public void init(String token, String tokenSecret) {
        RetrofitHttpOAuthConsumer oAuthConsumer = new RetrofitHttpOAuthConsumer(ConfigKeys.CONSUMER_API_KEY, ConfigKeys.CONSUMER_API_SECRET);
        oAuthConsumer.setTokenWithSecret(token, tokenSecret);
        OkClient client = new SigningOkClient(oAuthConsumer);
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(client)
                .build();
        mApi = adapter.create(TwitterApi.class);
    }

    public void postTweet(String tweet, Callback<PostTweetResponse> callback) {
        mApi.postTweet(tweet, EMPTY_BODY, callback);
    }

    public TwitterApi getApiService() { return  mApi; }
}