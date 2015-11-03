package com.arunpn.twitterapp.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arunpn.twitterapp.R;
import com.arunpn.twitterapp.adapter.FollowersArrayAdapter;
import com.arunpn.twitterapp.model.Followers;
import com.arunpn.twitterapp.model.TwitterUser;
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
 * Created by a1nagar on 11/2/15.
 */
public class FollowersDialog extends DialogFragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listView)
    ListView listView;
    TwitterApi apiService;
    TwitterService twitterService;
    FollowersArrayAdapter adapter;
    List<TwitterUser> userList;


    String screenName;
    int page = 0;
    final static int COUNT = 8;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new FollowersArrayAdapter(getActivity(), getFragmentManager(), new ArrayList<TwitterUser>());
        listView.setAdapter(adapter);
        getFollowers(page);

        listView.setOnScrollListener(endlessScrollListener);

    }

    private void getFollowers(int page) {
        apiService.getFollowers(screenName, new Callback<Followers>() {
            @Override
            public void success(Followers followers, Response response) {
                userList = followers.getUsers();
                adapter.addAll(userList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    protected EndlessScrollListener endlessScrollListener = new EndlessScrollListener() {
        @Override
        public boolean onLoadMore(int page, int totalItemsCount) {
            getFollowers(page);
            return true;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenName = getArguments().getString("followerName");

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.followers_dialog, container, false);
        ButterKnife.bind(this, view);
        twitterService = new TwitterService();
        twitterService.init(PrefUtil.getTwitterToken(getActivity().getApplicationContext()),
                PrefUtil.getTwitterTokenSecret(getActivity().getApplicationContext()));
        apiService = twitterService.getApiService();

        setupUI();
        setupToolBar();
        return view;

    }

    public void setupToolBar() {
        toolbar.setNavigationIcon(R.drawable.ic_twitter_icon_new);
        toolbar.setTitle("FOLLOWERS");
        toolbar.setBackgroundColor(getResources().getColor(R.color.style_color_primary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color_header));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    void setupUI() {
    }


    public static class Builder {

        public FollowersDialog build(String followerName) {
            FollowersDialog dialog = new FollowersDialog();
            Bundle args = new Bundle();
            args.putString("followerName", followerName);
            dialog.setArguments(args);
            dialog.setCancelable(true);
            return dialog;
        }
    }

}
