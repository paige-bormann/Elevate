package com.example.elevate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.elevate.ui.ClimbingNearMeActivity;

public class MapBroadcastReceiver extends BroadcastReceiver {
    private ClimbingNearMeActivity mClimbingNearMeActivity;
    private boolean mGPSEnabled;
    private boolean mNoConnectivity;

    public MapBroadcastReceiver(ClimbingNearMeActivity activity) {
        mClimbingNearMeActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
            String provider = intent.getStringExtra(LocationManager.EXTRA_PROVIDER_NAME);
            if (provider.equals("gps")) {
                mGPSEnabled = intent.getBooleanExtra(LocationManager.EXTRA_PROVIDER_ENABLED, true);
                if (mGPSEnabled) {
                    mClimbingNearMeActivity.updateView();
                } else {
                    mClimbingNearMeActivity.updateView();
                }
            }
        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            mNoConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if (mNoConnectivity) {
                Toast.makeText(context, "No internet connection - search disabled.", Toast.LENGTH_SHORT).show();
                mClimbingNearMeActivity.updateView();
            } else {
                mClimbingNearMeActivity.updateView();
            }
        }
    }
}
