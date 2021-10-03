package com.example.elevate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private Button mSignInButton;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_main);

        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        if (mSignInButton != null) {
            mSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Does nothing yet
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d(TAG, "onDestroy() called");
    }
}