package com.example.elevate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;
import com.example.elevate.ui.TutorialFragment;

public class TutorialBroadcastReceiver extends BroadcastReceiver {
    private TutorialFragment mTutorialFragment;

    public TutorialBroadcastReceiver(TutorialFragment fragment) {
        mTutorialFragment = fragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if (noConnectivity) {
                Toast.makeText(context, "No internet connection.", Toast.LENGTH_SHORT).show();
                mTutorialFragment.updateView(false);
            } else {
                mTutorialFragment.updateView(true);
            }
        }
    }
}
