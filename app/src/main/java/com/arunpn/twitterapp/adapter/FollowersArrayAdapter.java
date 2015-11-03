package com.arunpn.twitterapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arunpn.twitterapp.R;
import com.arunpn.twitterapp.activities.UserDetail;
import com.arunpn.twitterapp.fragments.ReplyDialog;
import com.arunpn.twitterapp.model.Tweet;
import com.arunpn.twitterapp.model.TwitterUser;
import com.arunpn.twitterapp.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by a1nagar on 10/25/15.
 */
public class FollowersArrayAdapter extends ArrayAdapter<TwitterUser> {
    Context mContext;
    FragmentManager fragmentManager;

    public FollowersArrayAdapter(Context context, FragmentManager fragmentManager, List<TwitterUser> objects) {
        super(context, 0, objects);
        this.mContext = context;
        this.fragmentManager = fragmentManager;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_followers_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        final TwitterUser twitterUser = getItem(position);
        holder.userName.setText(twitterUser.getUserName());
        holder.screenName.setText("@" + twitterUser.getScreenName());
        holder.description.setText("@" + twitterUser.getDescription());
        Picasso.with(mContext)
                .load(twitterUser.getProfileImageUrl())
                .resize(75, 75).transform(new CircleTransform()).into(holder.userImage);

        return convertView;
    }

    public class ViewHolder {
        @Bind(R.id.userImage)
        ImageView userImage;
        @Bind(R.id.userName)
        TextView userName;
        @Bind(R.id.screenName)
        TextView screenName;
        @Bind(R.id.description)
        TextView description;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
