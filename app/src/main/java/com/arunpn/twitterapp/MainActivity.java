package com.arunpn.twitterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.arunpn.twitterapp.adapter.TweetsArrayAdapter;
import com.arunpn.twitterapp.model.Tweet;
import com.arunpn.twitterapp.service.TwitterApi;
import com.arunpn.twitterapp.service.TwitterService;
import com.arunpn.twitterapp.utils.PrefUtil;

import java.util.ArrayList;
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
    List<Tweet> tweetList;
    @Bind(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (!PrefUtil.isAuthenticated(getApplicationContext())) {
            Intent intent = new Intent(this, TwitterLoginActivity.class);
            startActivity(intent);
        }
        ;

        twitterService = new TwitterService();
        twitterService.init(PrefUtil.getTwitterToken(getApplicationContext()),
                PrefUtil.getTwitterTokenSecret(getApplicationContext()));
        apiService = twitterService.getApiService();
        adapter = new TweetsArrayAdapter(this, new ArrayList<Tweet>());
        listView.setAdapter(adapter);

        apiService.getHomeTimeLine(new Callback<List<Tweet>>() {
            @Override
            public void success(List<Tweet> tweets, Response response) {
                tweetList = tweets;
                adapter.addAll(tweets);
                adapter.notifyDataSetChanged();
//                for(Tweet tweet : tweets) {
//                    Log.e("x",tweet.getUser().getUserName());
//                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
