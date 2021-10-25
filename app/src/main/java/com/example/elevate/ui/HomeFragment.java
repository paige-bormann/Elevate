package com.example.elevate.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.UserAccount;

import timber.log.Timber;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Button mSkillSurveyButton;
    private Button mClimbingPlanButton;
    private Button mNearMeButton;
    private Button mProgressButton;
    private Button mSettingsButton;
    private TextView mSkillLevelTextView;

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
                actionBar.setSubtitle(getResources().getString(R.string.home));
            }
        }
        catch (NullPointerException npe) {
            Timber.e("Could not set subtitle");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView() called");

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        UserAccount user = mElevateViewModel.getCurrentUser();

        mSkillLevelTextView = v.findViewById(R.id.skill_level_textview);
        mSkillLevelTextView.setText("Current Skill Level: " + user.mCLevel);
        mSkillSurveyButton = v.findViewById(R.id.survey_button);
        if (mSkillSurveyButton != null) {
            mSkillSurveyButton.setOnClickListener(this);
        }
        mClimbingPlanButton = v.findViewById(R.id.climbing_plan_button);
        if (mClimbingPlanButton != null) {
            mClimbingPlanButton.setOnClickListener(this);
        }
        mNearMeButton = v.findViewById(R.id.near_me_button);
        if (mNearMeButton != null) {
            mNearMeButton.setOnClickListener(this);
        }
        mProgressButton = v.findViewById(R.id.progress_button);
        if (mProgressButton != null) {
            mProgressButton.setOnClickListener(this);
        }
        mSettingsButton = v.findViewById(R.id.settings_button);
        if (mSettingsButton != null) {
            mSettingsButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.survey_button) {
            FragmentManager fm = getParentFragmentManager();
            Fragment fragment = new QuestionOneFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
        } else if (viewId == R.id.climbing_plan_button) {
            // open climbing plan page
        } else if (viewId == R.id.near_me_button) {
            // open near me page
        } else if (viewId == R.id.progress_button) {
            // open progress page
        } else if (viewId == R.id.settings_button) {
            FragmentManager fm = getParentFragmentManager();
            Fragment fragment = new SettingsFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
        }
    }
}