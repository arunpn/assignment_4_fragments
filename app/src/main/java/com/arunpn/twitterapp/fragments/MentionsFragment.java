package com.arunpn.twitterapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arunpn.twitterapp.R;
import com.arunpn.twitterapp.activities.TweetDetailActivity;
import com.arunpn.twitterapp.adapter.TweetsArrayAdapter;
import com.arunpn.twitterapp.model.Tweet;
import com.arunpn.twitterapp.service.TwitterApi;
import com.arunpn.twitterapp.service.TwitterService;
import com.arunpn.twitterapp.utils.EndlessScrollListener;
import com.arunpn.twitterapp.utils.PrefUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by a1nagar on 10/31/15.
 */
public class MentionsFragment extends Fragment {

    TwitterApi apiService;
    TwitterService twitterService;
    TweetsArrayAdapter adapter;
    List<Tweet> tweetList;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    int page = 0;
    final static int COUNT = 8;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mentions, container, false);
        ButterKnife.bind(this, view);
        twitterService = new TwitterService();
        twitterService.init(PrefUtil.getTwitterToken(getActivity().getApplicationContext()),
                PrefUtil.getTwitterTokenSecret(getActivity().getApplicationContext()));
        apiService = twitterService.getApiService();

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        adapter = new TweetsArrayAdapter(getActivity(), new ArrayList<Tweet>());
        listView.setAdapter(adapter);
        getMentionsTimeLine(page);

        listView.setOnScrollListener(endlessScrollListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tweet tweetDetail = (Tweet) adapter.getItem(i);
                Intent intent = new Intent(getActivity().getApplicationContext(), TweetDetailActivity.class);
                intent.putExtra("tweet", tweetDetail);
                startActivity(intent);
            }
        });

    }


    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getMentionsTimeLine(page);
        }
    };

    public void getMentionsTimeLine(final int page) {
        apiService.getMentionsTimeLine(COUNT, page, new Callback<List<Tweet>>() {
            @Override
            public void success(List<Tweet> tweets, Response response) {
                tweetList = tweets;
                if (page == 0) {
                    adapter.clear();
                }
                adapter.addAll(tweets);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    protected EndlessScrollListener endlessScrollListener = new EndlessScrollListener() {
        @Override
        public boolean onLoadMore(int page, int totalItemsCount) {
            getMentionsTimeLine(page);
            return true;
        }
    };
}
