package com.arunpn.twitterapp.model;

import java.io.Serializable;

/**
 * Created by a1nagar on 10/26/15.
 */
public class Url implements Serializable {
    String url;
    String expandedUrl;
    String displayUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }
}
