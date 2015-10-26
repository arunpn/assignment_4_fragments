package com.arunpn.twitterapp.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.arunpn.twitterapp.model.Tweet;

import java.util.List;

/**
 * Created by a1nagar on 10/25/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapter(Context context, List<Tweet> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

}
