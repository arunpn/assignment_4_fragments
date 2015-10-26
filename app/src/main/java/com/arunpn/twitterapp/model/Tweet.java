package com.arunpn.twitterapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by a1nagar on 10/25/15.
 */
public class Tweet {

    @SerializedName("text")
    String body;
    long uid;
    @SerializedName("created_at")
    String createdAt;
    TwitterUser user;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public TwitterUser getUser() {
        return user;
    }

    public void setUser(TwitterUser user) {
        this.user = user;
    }
}
