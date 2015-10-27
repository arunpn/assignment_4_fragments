package com.arunpn.twitterapp.service;

import com.arunpn.twitterapp.utils.ConfigKeys;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by a1nagar on 10/25/15.
 */
public class TwitterService {
    public static final String BASE_URL = "https://api.twitter.com/1.1";

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


    public TwitterApi getApiService() {
        return mApi;
    }
}