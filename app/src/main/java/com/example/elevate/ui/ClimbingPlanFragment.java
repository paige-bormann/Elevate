package com.example.elevate.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.UserAccount;
import com.example.elevate.model.Workout;

import java.util.List;

import timber.log.Timber;

public class ClimbingPlanFragment extends Fragment implements View.OnClickListener {
    private ElevateViewModel mElevateViewModel;
    private Button mNewWorkoutButton;
    private Button mUpdateWorkoutButton;
    private Button mDeleteWorkoutButton;
    private TextView mNameTextView;
    private TextView mStyleTextView;
    private TextView mGradeTextView;
    private TextView mTutorialTextView;
    private List<Workout> allWorkouts;

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
                actionBar.setSubtitle(getResources().getString(R.string.climbing_plan));
            }
        }
        catch (NullPointerException npe) {
            Timber.e("Could not set subtitle");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView() called");

        View v = inflater.inflate(R.layout.fragment_workouts, container, false);

        mNameTextView = v.findViewById(R.id.name_display_textview);
        mStyleTextView = v.findViewById(R.id.style_display_textview);
        mGradeTextView = v.findViewById(R.id.grade_display_textview);
        mTutorialTextView = v.findViewById(R.id.tutorial_display_textview);

        mNewWorkoutButton = v.findViewById(R.id.new_workout_button);
        if (mNewWorkoutButton != null) {
            mNewWorkoutButton.setOnClickListener(this);
        }
        mUpdateWorkoutButton = v.findViewById(R.id.update_workout_button);
        if (mUpdateWorkoutButton != null) {
            mUpdateWorkoutButton.setOnClickListener(this);
        }
        mDeleteWorkoutButton = v.findViewById(R.id.delete_workout_button);
        if (mDeleteWorkoutButton != null) {
            mDeleteWorkoutButton.setOnClickListener(this);
        }

        Activity activity = requireActivity();
        mElevateViewModel.getAllWorkouts().observe((LifecycleOwner) activity, new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workouts) {
                allWorkouts = workouts;
                if (allWorkouts != null && allWorkouts.size() >=  1) {
                    mNameTextView.setText(allWorkouts.get(0).getName());
                    mStyleTextView.setText(allWorkouts.get(0).getStyle());
                    mGradeTextView.setText(String.valueOf(allWorkouts.get(0).getGrade()));
                    mTutorialTextView.setText(allWorkouts.get(0).getTutorial());
                } else {
                    mNameTextView.setText("Name");
                    mStyleTextView.setText("Style");
                    mGradeTextView.setText("Grade");
                    mTutorialTextView.setText("Tutorial");
                }
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.new_workout_button) {
            FragmentManager fm = getParentFragmentManager();
            Fragment fragment = new NewWorkoutFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
        } else if (viewId == R.id.update_workout_button) {
            FragmentManager fm = getParentFragmentManager();
            Fragment fragment = new UpdateWorkoutFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
        } else if (viewId == R.id.delete_workout_button) {
            if (allWorkouts != null && allWorkouts.size() >=  1) {
                mElevateViewModel.delete(allWorkouts.get(0));
            }
        }
    }
}
