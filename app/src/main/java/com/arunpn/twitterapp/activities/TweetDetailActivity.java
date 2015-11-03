package com.arunpn.twitterapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.arunpn.twitterapp.R;
import com.arunpn.twitterapp.model.Tweet;
import com.arunpn.twitterapp.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TweetDetailActivity extends AppCompatActivity {

    Tweet tweet;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.screenName)
    TextView screenName;
    @Bind(R.id.tweetBody)
    TextView tweetBody;
    @Bind(R.id.tweetPage)
    WebView tweetPage;
    @Bind(R.id.createdDate)
    TextView createdDate;
    @Bind(R.id.userProfileImage)
    ImageView userProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        ButterKnife.bind(this);
        setupToolBar();

        tweet = (Tweet) getIntent().getSerializableExtra("tweet");
        userName.setText(tweet.getUser().getUserName());
        screenName.setText(tweet.getUser().getScreenName());
        tweetBody.setText(tweet.getBody());
        tweetPage.setWebViewClient(new MyWebViewClient());
        if(tweet.getEntities().getUrls().size() > 0 ) {
            tweetPage.loadUrl(tweet.getEntities().getUrls().get(0).getUrl());
        }
        else {
            tweetPage.setVisibility(View.GONE);
        }
        tweetPage.requestFocus();
        createdDate.setText(tweet.getCreatedAt());
        Picasso.with(this)
                .load(tweet.getUser().getProfileImageUrl())
                .resize(75, 75).transform(new CircleTransform()).into(userProfileImage);


    }

    void setupToolBar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tweet Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_twitter_icon_new);
        toolbar.setBackgroundColor(getResources().getColor(R.color.style_color_primary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color_header));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
