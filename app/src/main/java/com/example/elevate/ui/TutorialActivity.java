package com.example.elevate.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.elevate.R;
import com.example.elevate.TutorialBroadcastReceiver;
import com.example.elevate.YouTubeConfig;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import timber.log.Timber;

public class TutorialActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    private String mTutorial;
    private YouTubePlayerFragment mYouTubePlayerFragment;
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
    public void onResume() {
        super.onResume();
        try {
            ActionBar actionBar = TutorialActivity.this.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setSubtitle(getResources().getString(R.string.tutorial));
            }
        }
        catch (NullPointerException npe) {
            Timber.e("Could not set subtitle");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mYouTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        mYouTubePlayerFragment.initialize(YouTubeConfig.getAPIKey(), this);

        Intent intent = getIntent();
        mTutorial = intent.getExtras().getString("tutorial");
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            String key = parseTutorial(mTutorial);
            youTubePlayer.loadVideo(key);
        } else {
            youTubePlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            String error = String.format("There was an error initializing the YouTubePlayer (%1$s)",
                    youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    private String parseTutorial(String url) {
        int start = url.indexOf("=") + 1;
        int end = url.indexOf("&");
        return url.substring(start, end);
    }
}
