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
import androidx.lifecycle.LiveData;
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
    private TextView mNameEditText;
    private TextView mStyleEditText;
    private TextView mGradeEditText;
    private TextView mTutorialEditText;

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

        LiveData<List<Workout>> workoutListData = mElevateViewModel.getAllWorkouts();
        List<Workout> workoutList = workoutListData.getValue();

        mNameEditText = v.findViewById(R.id.name_edittext);
        mStyleEditText = v.findViewById(R.id.style_edittext);
        mGradeEditText = v.findViewById(R.id.grade_edittext);
        mTutorialEditText = v.findViewById(R.id.tutorial_edittext);
        if (workoutList != null && workoutList.size() >=  1) {
            mNameEditText.setText(workoutList.get(0).getName());
            mStyleEditText.setText(workoutList.get(0).getStyle());
            mGradeEditText.setText(String.valueOf(workoutList.get(0).getGrade()));
            mTutorialEditText.setText(workoutList.get(0).getTutorial());
        }

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
            LiveData<List<Workout>> workoutListData = mElevateViewModel.getAllWorkouts();
            List<Workout> workoutList = workoutListData.getValue();
            if (workoutList != null && workoutList.size() >=  1) {
                mElevateViewModel.delete(workoutList.get(0));
            }
        }
    }
}
