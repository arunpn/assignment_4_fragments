package com.arunpn.twitterapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arunpn.twitterapp.R;
import com.arunpn.twitterapp.adapter.HomeFragmentPagerAdapter;
import com.arunpn.twitterapp.adapter.TweetsArrayAdapter;
import com.arunpn.twitterapp.fragments.MentionsFragment;
import com.arunpn.twitterapp.fragments.TimeLineFragment;
import com.arunpn.twitterapp.model.PostTweetResponse;
import com.arunpn.twitterapp.model.Tweet;
import com.arunpn.twitterapp.service.TwitterApi;
import com.arunpn.twitterapp.service.TwitterService;
import com.arunpn.twitterapp.utils.PrefUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    TwitterApi apiService;
    TwitterService twitterService;
    TweetsArrayAdapter adapter;
    HomeFragmentPagerAdapter homeFragmentPagerAdapter;
    List<Tweet> tweetList;
//    @Bind(R.id.listView) ListView listView;
    @Bind(R.id.toolbar) Toolbar toolbar;
//    @Bind(R.id.swipeRefresh) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.tabLayout) TabLayout tabLayout;
    @Bind(R.id.viewPager) ViewPager viewPager;
    int page = 0;
    final static int COUNT = 8;
    public static final String EMPTY_BODY = "{}";
    int pendingChars = 140;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.tab_layout);
        ButterKnife.bind(this);
        setupToolbar();
        setupTabs();
        istwitterAuthenticated();
        setupTwitterService();
    }

    private void setupTabs() {
        homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(homeFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(tabSelectedListener);
    }

    private void setupTwitterService() {
        twitterService = new TwitterService();
        twitterService.init(PrefUtil.getTwitterToken(getApplicationContext()),
                PrefUtil.getTwitterTokenSecret(getApplicationContext()));
        apiService = twitterService.getApiService();

    }

    private void istwitterAuthenticated() {
        if (!PrefUtil.isAuthenticated(getApplicationContext())) {
            Intent intent = new Intent(this, TwitterLoginActivity.class);
            startActivity(intent);
        }
        ;
    }


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        toolbar.setNavigationIcon(R.drawable.ic_twitter_icon);
        toolbar.setBackgroundColor(getResources().getColor(R.color.style_color_primary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color_header));

    }

    TabLayout.OnTabSelectedListener  tabSelectedListener =   new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_compose) {
            showComposeDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showComposeDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.compose_layout, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Compose Tweet").setView(convertView);

        final EditText tweetBody = (EditText) convertView.findViewById(R.id.composeTweet);
        final TextView tweetCount = (TextView) convertView.findViewById(R.id.textCounter);


        tweetBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pendingChars = 140 - tweetBody.getText().length();
                tweetCount.setText(Integer.toString(pendingChars));

            }
        });


        alertDialog.setPositiveButton("TWEET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                postTweet(tweetBody.getText().toString());
                
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create();
        alertDialog.show();

    }

    public void postTweet(String tweetBody) {
        apiService.postTweet(tweetBody, EMPTY_BODY, new Callback<PostTweetResponse>() {
            @Override
            public void success(PostTweetResponse postTweetResponse, Response response) {
                Log.e("x", String.valueOf(response.getStatus()));
                callFragment();

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    void callFragment() {
       Fragment fragment = homeFragmentPagerAdapter.getRegisteredFragment(viewPager.getCurrentItem());

        if ( fragment instanceof  TimeLineFragment ) {
            TimeLineFragment timeLineFragment = (TimeLineFragment) fragment;
            timeLineFragment.getHomeTimeLine(0);
        }
        else
        {
            MentionsFragment mentionsFragment = (MentionsFragment) fragment;
            mentionsFragment.getMentionsTimeLine(0);
        }


    }

}
