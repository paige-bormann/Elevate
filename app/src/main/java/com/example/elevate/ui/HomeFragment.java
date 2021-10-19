package com.example.elevate.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.elevate.R;

import timber.log.Timber;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Button mSkillSurveyButton;
    private Button mClimbingPlanButton;
    private Button mNearMeButton;
    private Button mProgressButton;
    private TextView mSkillLevelTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate() called");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView() called");

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mSkillLevelTextView = v.findViewById(R.id.skill_level_textview);
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

        return v;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.survey_button) {
            // open survey page
        } else if (viewId == R.id.climbing_plan_button) {
            // open climbing plan page
        } else if (viewId == R.id.near_me_button) {
            // open near me page
        } else if (viewId == R.id.progress_button) {
            // open progress page
        }
    }
}