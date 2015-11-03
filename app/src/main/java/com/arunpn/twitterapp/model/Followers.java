package com.arunpn.twitterapp.model;

import java.util.List;

/**
 * Created by a1nagar on 11/2/15.
 */
public class Followers {
    List<TwitterUser> users;

    public List<TwitterUser> getUsers() {
        return users;
    }

    public void setUsers(List<TwitterUser> users) {
        this.users = users;
    }
}
