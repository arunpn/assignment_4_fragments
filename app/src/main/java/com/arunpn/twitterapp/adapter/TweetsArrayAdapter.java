package com.arunpn.twitterapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arunpn.twitterapp.R;
import com.arunpn.twitterapp.model.Tweet;
import com.arunpn.twitterapp.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by a1nagar on 10/25/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    Context mContext;

    public TweetsArrayAdapter(Context context, List<Tweet> objects) {
//        super(context, android.R.layout.simple_list_item_1, objects);
        super(context, 0, objects);
        this.mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_timeline, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Tweet tweet = getItem(position);
        holder.createdDate.setText(tweet.getRelativeTimeAgo());
        holder.tweetBody.setText(tweet.getBody());
        holder.userName.setText(tweet.getUser().getUserName());
        Picasso.with(mContext)
                .load(tweet.getUser().getProfileImageUrl())
                .resize(75, 75).transform(new CircleTransform()).into(holder.userProfileImage);


        return convertView;
    }

    public class ViewHolder {
        @Bind(R.id.userProfileImage)
        ImageView userProfileImage;
        @Bind(R.id.userName)
        TextView userName;
        @Bind(R.id.createdDate)
        TextView createdDate;
        @Bind(R.id.tweetBody)
        TextView tweetBody;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
