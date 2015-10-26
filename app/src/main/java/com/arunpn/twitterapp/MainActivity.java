package com.arunpn.twitterapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.arunpn.twitterapp.model.Tweet;
import com.arunpn.twitterapp.service.TwitterApi;
import com.arunpn.twitterapp.service.TwitterService;
import com.arunpn.twitterapp.utils.PrefUtil;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    TwitterApi apiService;
    TwitterService twitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!PrefUtil.isAuthenticated(getApplicationContext())) {
            Intent intent = new Intent(this, TwitterLoginActivity.class);
            startActivity(intent);
        };

            twitterService = new TwitterService();
            twitterService.init(PrefUtil.getTwitterToken(getApplicationContext()),
                    PrefUtil.getTwitterTokenSecret(getApplicationContext()));
            apiService = twitterService.getApiService();



        apiService.getHomeTimeLine( new Callback<List<Tweet>>() {
            @Override
            public void success(List<Tweet> tweets, Response response) {

                for(Tweet tweet : tweets) {
                    Log.e("x",tweet.getUser().getUserName());
                }
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
