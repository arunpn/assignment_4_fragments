package com.arunpn.twitterapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by a1nagar on 10/25/15.
 */
public class PostTweetResponse {
    @SerializedName("id_str")
    private String mId;
    @SerializedName("user")
    private TwitterUser mUser;
    @SerializedName("text")
    private String mText;
    @SerializedName("source")
    private String mSource;

    public String getId() {
        return mId;
    }

    public TwitterUser getUser() {
        return mUser;
    }

    public String getText() {
        return mText;
    }

    public String getSource() {
        return mSource;
    }


}