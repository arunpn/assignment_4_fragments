package com.arunpn.twitterapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by a1nagar on 10/26/15.
 */
public class Entities implements Serializable {
    List<Url> urls;

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }
}
