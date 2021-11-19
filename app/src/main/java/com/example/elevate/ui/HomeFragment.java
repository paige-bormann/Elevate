package com.example.elevate.ui;

import android.app.Activity;
import android.content.Context;
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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.UserAccount;

import java.util.List;

import timber.log.Timber;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Button mSkillSurveyButton;
    private Button mClimbingPlanButton;
    private Button mNearMeButton;
    private Button mProgressButton;
    private Button mSettingsButton;
    private TextView mSkillLevelTextView;
    private TextView mGoalLevelTextView;

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

        View v;
        Activity activity = requireActivity();
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            v = inflater.inflate(R.layout.fragment_home_land, container, false);
        } else {
            v = inflater.inflate(R.layout.fragment_home, container, false);
        }

        UserAccount user = mElevateViewModel.getCurrentUser();

        mSkillLevelTextView = v.findViewById(R.id.skill_level_textview);
        mSkillLevelTextView.setText("Current Skill Level: " + user.mCLevel);
        mGoalLevelTextView = v.findViewById(R.id.goal_skill_level_textview);
        mGoalLevelTextView.setText("Goal Skill Level: " + user.mGLevel);
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
        Activity activity = requireActivity();
        final int viewId = v.getId();
        if (viewId == R.id.survey_button) {
            FragmentManager fm = getParentFragmentManager();
            Fragment fragment = new QuestionOneFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
        } else if (viewId == R.id.climbing_plan_button) {
            FragmentManager fm = getParentFragmentManager();
            Fragment fragment = new ClimbingPlanFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
        } else if (viewId == R.id.near_me_button) {
            // open near me page
            /*
            FragmentManager fm=getParentFragmentManager();
            Fragment fragment = new ClimbingNearMeFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();*/
            //open near me activity
            Intent intent = new Intent(getActivity(), ClimbingNearMeActivity.class);
            startActivity(intent);

        } else if (viewId == R.id.progress_button) {
            FragmentManager fm = getParentFragmentManager();
            Fragment fragment = new ProgressFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
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