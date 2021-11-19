package com.example.elevate.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.UserAccount;

import timber.log.Timber;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private TextView mUsernameTextView;
    private TextView mCurrentLevelTextView;
    private TextView mGoalLevelTextView;
    private Button mSignOutButton;
    private Button mDeleteAccountButton;

    private ElevateViewModel mElevateViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate() called");

        Activity activity = requireActivity();
        mElevateViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ElevateViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            AppCompatActivity activity = (AppCompatActivity) requireActivity();

            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setSubtitle(getResources().getString(R.string.settings));
            }
        }
        catch (NullPointerException npe) {
            Timber.e("Could not set subtitle");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView() called");

        View v;
        Activity activity = requireActivity();
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            v = inflater.inflate(R.layout.fragment_settings_land, container, false);
        } else {
            v = inflater.inflate(R.layout.fragment_settings, container, false);
        }
        UserAccount user = mElevateViewModel.getCurrentUser();

        mUsernameTextView = v.findViewById(R.id.username_display_textview);
        mUsernameTextView.setText(user.mName);
        mCurrentLevelTextView = v.findViewById(R.id.current_skill_display_textview);
        mCurrentLevelTextView.setText(String.valueOf(user.mCLevel));
        mGoalLevelTextView = v.findViewById(R.id.goal_skill_display_textview);
        mGoalLevelTextView.setText(String.valueOf(user.mGLevel));

        mSignOutButton = v.findViewById(R.id.sign_out_button);
        if (mSignOutButton != null) {
            mSignOutButton.setOnClickListener(this);
        }
        mDeleteAccountButton = v.findViewById(R.id.delete_account_button);
        if (mDeleteAccountButton != null) {
            mDeleteAccountButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.sign_out_button) {
            Activity activity = requireActivity();
            startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } else if (viewId == R.id.delete_account_button) {
            FragmentManager manager = getParentFragmentManager();
            DeleteAccountDialogFragment fragment = new DeleteAccountDialogFragment();
            fragment.show(manager, "delete_account");
        }
    }
}
