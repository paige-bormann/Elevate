package com.example.elevate.ui;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.TutorialBroadcastReceiver;
import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.Workout;

import timber.log.Timber;

public class TutorialFragment extends Fragment {
    private WebView mWebView;
    private ElevateViewModel mElevateViewModel;
    private Workout mWorkout;
    private TextView mInternetHint;

    TutorialActivity temp = new TutorialActivity();
    TutorialBroadcastReceiver mTutorialBroadcastReceiver = new TutorialBroadcastReceiver(temp);

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(mTutorialBroadcastReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mTutorialBroadcastReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate() called");
        FragmentActivity activity = requireActivity();
        mElevateViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ElevateViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            AppCompatActivity activity = (AppCompatActivity) requireActivity();

            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setSubtitle(getResources().getString(R.string.tutorial));
            }
        }
        catch (NullPointerException npe) {
            Timber.e("Could not set subtitle");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView() called");

        View v = inflater.inflate(R.layout.fragment_tutorial, container, false);
        mInternetHint = v.findViewById(R.id.internet_textview);
        mInternetHint.setVisibility(View.INVISIBLE);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int id = Integer.parseInt(bundle.get("id").toString());
            mWorkout = mElevateViewModel.getWorkout(id);

            mWebView = (WebView) v.findViewById(R.id.tutorial_webview);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            //webSettings.setDomStorageEnabled(true);
            //mWebView.setWebChromeClient(new WebChromeClient());
            //mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            mWebView.loadUrl(mWorkout.getTutorial());
            //mWebView.pauseTimers();
            //mWebView.resumeTimers();
        }

        return v;
    }

    public void updateView(boolean connection) {
        if (connection) {
            mInternetHint.setVisibility(View.INVISIBLE);
            mWebView.setVisibility(View.VISIBLE);
            mWebView.loadUrl(mWorkout.getTutorial());
        } else {
            mInternetHint.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.INVISIBLE);
        }
    }
}

