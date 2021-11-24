package com.example.elevate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.elevate.ui.TutorialActivity;

public class TutorialBroadcastReceiver extends BroadcastReceiver {
    private TutorialActivity mTutorialActivity;
    private boolean previousConnection = true;

    public TutorialBroadcastReceiver(TutorialActivity activity) {
        mTutorialActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if (noConnectivity) {
                Toast.makeText(context, "No internet connection.", Toast.LENGTH_SHORT).show();
                previousConnection = false;
            } else if (!previousConnection) {
                Toast.makeText(context, "Re-established internet connection.", Toast.LENGTH_SHORT).show();
                previousConnection = true;
            }
        }
    }
}
