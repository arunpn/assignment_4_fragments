package com.arunpn.twitterapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arunpn.twitterapp.R;
import com.arunpn.twitterapp.adapter.TweetsArrayAdapter;
import com.arunpn.twitterapp.model.Tweet;
import com.arunpn.twitterapp.model.TwitterUser;
import com.arunpn.twitterapp.service.TwitterApi;
import com.arunpn.twitterapp.service.TwitterService;
import com.arunpn.twitterapp.utils.CircleTransform;
import com.arunpn.twitterapp.utils.EndlessScrollListener;
import com.arunpn.twitterapp.utils.PrefUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserDetail extends AppCompatActivity {

    TwitterApi apiService;
    TwitterService twitterService;
    TweetsArrayAdapter adapter;
    TwitterUser userData;
    List<Tweet> userTweets;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.screenName)   TextView screenName;
    @Bind(R.id.description)   TextView description;
    @Bind(R.id.userName)   TextView userTwitterName;
    @Bind(R.id.userImage) ImageView userImage;
    @Bind(R.id.followerCount) TextView followerCount;
    @Bind(R.id.friendsCount) TextView friendsCount;
    String userName;

    int page = 0;
    final static int COUNT = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        twitterService = new TwitterService();
        twitterService.init(PrefUtil.getTwitterToken(getApplicationContext()),
                PrefUtil.getTwitterTokenSecret(getApplicationContext()));
        apiService = twitterService.getApiService();
        adapter = new TweetsArrayAdapter(this, new ArrayList<Tweet>());
        listView.setAdapter(adapter);
        setupToolBar();

        Intent intent = getIntent();
        userName = intent.getStringExtra("user_name");
        getUserDetails(userName,page);

        listView.setOnScrollListener(endlessScrollListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tweet tweetDetail = (Tweet) adapter.getItem(i);
                Intent intent = new Intent(getApplicationContext(), TweetDetailActivity.class);
                intent.putExtra("tweet", tweetDetail);
                startActivity(intent);
            }
        });
    }

    protected EndlessScrollListener endlessScrollListener = new EndlessScrollListener() {
        @Override
        public boolean onLoadMore(int page, int totalItemsCount) {
            getUserDetails(userName,page);
            return true;
        }
    };

    public void getUserDetails(final String userName, final int page) {

        apiService.getUser(userName, new Callback<TwitterUser>() {
            @Override
            public void success(TwitterUser twitterUser, Response response) {
                userData = twitterUser;

                apiService.getUserTimeLine(COUNT, page, userName, new Callback<List<Tweet>>() {
                    @Override
                    public void success(List<Tweet> tweets, Response response) {
                        userTweets = tweets;
                        if (page == 0) {
                            adapter.clear();
                        }
                        adapter.addAll(tweets);
                        adapter.notifyDataSetChanged();
                        populateData();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    public void setupToolBar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_twitter_icon);
        toolbar.setBackgroundColor(getResources().getColor(R.color.style_color_primary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color_header));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void populateData() {
        friendsCount.setText(userData.getFriendsCount().toString());
        followerCount.setText(userData.getFollowersCount().toString());
        screenName.setText(userData.getScreenName());
        userTwitterName.setText(userData.getUserName());
        description.setText(userData.getDescription());
        Picasso.with(this)
                .load(userData.getProfileImageUrl())
                .resize(75, 75).transform(new CircleTransform()).into(userImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
