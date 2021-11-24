package com.example.elevate.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.R;
import com.example.elevate.TutorialBroadcastReceiver;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.Workout;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import timber.log.Timber;

public class TutorialActivity extends YouTubeBaseActivity {
    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private String mTutorial;
    private TextView mInternetHint;
    TutorialBroadcastReceiver mTutorialBroadcastReceiver = new TutorialBroadcastReceiver(this);

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mTutorialBroadcastReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(mTutorialBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        mInternetHint = (TextView) findViewById(R.id.internet_textview);

        Intent intent = getIntent();
        mTutorial = intent.getExtras().getString("tutorial");

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                String key = parseTutorial(mTutorial);
                youTubePlayer.loadVideo(key);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        mYouTubePlayerView.initialize(YouTubeConfig.getAPIKey(), mOnInitializedListener);
    }

    private String parseTutorial(String url) {
        int start = url.indexOf("=") + 1;
        int end = url.indexOf("&");
        return url.substring(start, end);
    }

    public void updateView(boolean connection) {
        if (connection) {
            mInternetHint.setVisibility(View.INVISIBLE);
            mYouTubePlayerView.setVisibility(View.VISIBLE);
            if (mYouTubePlayerView == null) {
                mYouTubePlayerView.initialize(YouTubeConfig.getAPIKey(), mOnInitializedListener);
            }
        } else {
            mInternetHint.setVisibility(View.VISIBLE);
            mYouTubePlayerView.setVisibility(View.INVISIBLE);
        }
    }
}
