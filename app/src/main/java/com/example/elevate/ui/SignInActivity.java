package com.example.elevate.ui;

import androidx.fragment.app.Fragment;
import android.os.Bundle;

import timber.log.Timber;

/**
 * Activity for user sign in.
 *
 * Created by paige-bormann on 10/08/2021.
 */

public class SignInActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SignInFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate() called");
    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.d("onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.d("onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.d("onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy() called");
    }
}