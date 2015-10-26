package com.arunpn.twitterapp.service;


import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Created by a1nagar on 10/25/15.
 */

public class SigningOkClient extends OkClient {

    private final RetrofitHttpOAuthConsumer mOAuthConsumer;

    public SigningOkClient(RetrofitHttpOAuthConsumer consumer) {
        mOAuthConsumer = consumer;
    }

    public SigningOkClient(OkHttpClient client, RetrofitHttpOAuthConsumer consumer) {
        super(client);
        mOAuthConsumer = consumer;
    }

    @Override
    public Response execute(Request request) throws IOException {
        Request requestToSend = request;
        try {
            HttpRequestAdapter signedAdapter = (HttpRequestAdapter) mOAuthConsumer.sign(request);
            requestToSend = (Request) signedAdapter.unwrap();
        } catch (OAuthMessageSignerException e) {
            // Fail to sign, ignore
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            // Fail to sign, ignore
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            // Fail to sign, ignore
            e.printStackTrace();
        }
        return super.execute(requestToSend);
    }

}