package com.arunpn.twitterapp.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arunpn.twitterapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by a1nagar on 11/2/15.
 */
public class ReplyDialog extends DialogFragment {

    public interface ReplyDialogListener {
        void onSubmitTweet(String tweet);
    }

    ReplyDialogListener replyDialogListener;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.replyTextarea)
    EditText tweetBody;
    @Bind(R.id.tweetCount)
    TextView tweetCount;
    @Bind(R.id.tweetButton)
    Button tweetButton;

    String screenName;
    int pendingChars = 140;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenName = getArguments().getString("screenName");

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        replyDialogListener = (ReplyDialogListener) activity;
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
        View view = inflater.inflate(R.layout.reply_dialog, container, false);
        ButterKnife.bind(this, view);


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


        tweetBody.setText("@" + screenName + " ");
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replyDialogListener.onSubmitTweet(tweetBody.getText().toString());
                dismiss();
            }
        });

        setupUI();
        setupToolBar();
        return view;

    }

    public void setupToolBar() {
        toolbar.setNavigationIcon(R.drawable.ic_twitter_icon_new);
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

        public ReplyDialog build(String screenName) {
            ReplyDialog dialog = new ReplyDialog();
            Bundle args = new Bundle();
            args.putString("screenName", screenName);
            dialog.setArguments(args);
            dialog.setCancelable(true);
            return dialog;
        }
    }

}
