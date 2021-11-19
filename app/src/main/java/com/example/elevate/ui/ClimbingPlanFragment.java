package com.example.elevate.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchUIUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.UserAccount;
import com.example.elevate.model.Workout;
import com.example.elevate.model.WorkoutAdapter;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ClimbingPlanFragment extends Fragment implements WorkoutAdapter.OnWorkoutListener {
    private ElevateViewModel mElevateViewModel;
    private List<Workout> mWorkouts = new ArrayList<>();
    private TextView mHint;

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

        Activity activity = requireActivity();
        View v = inflater.inflate(R.layout.fragment_climbing_plan, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.climbing_plan_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        final WorkoutAdapter adapter = new WorkoutAdapter(mWorkouts, this);
        recyclerView.setAdapter(adapter);
        mHint = v.findViewById(R.id.hint_textview);
        mHint.setVisibility(View.INVISIBLE);

        mElevateViewModel.getAllWorkouts().observe((LifecycleOwner) activity, new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workoutList) {
                // Filter workouts to only include incomplete workouts within user's current/goal grade
                mWorkouts.clear();
                for (int i = 0; i < workoutList.size(); i++) {
                    Workout currentWorkout = workoutList.get(i);
                    if (!currentWorkout.getCompleted() &&
                            checkGradeWithinGoal(currentWorkout, mElevateViewModel.getCurrentUser())) {
                        mWorkouts.add(currentWorkout);
                    }
                }
                adapter.setWorkouts(mWorkouts);

                if (mElevateViewModel.getCurrentUser().mCLevel == 0 && mWorkouts.size() == 0) {
                    mHint.setVisibility(View.VISIBLE);
                    mHint.setText(getResources().getString(R.string.climbing_plan_hint_need_skill));
                } else if (mElevateViewModel.getCurrentUser().mCLevel != 0 && mWorkouts.size() == 0) {
                    mHint.setVisibility(View.VISIBLE);
                    mHint.setText(getResources().getString(R.string.climbing_plan_hint_all_done));
                } else {
                    mHint.setVisibility(View.INVISIBLE);
                }
            }
        });

        return v;
    }

    @Override
    public void onWorkoutClick(int position) {
        Workout workout = mWorkouts.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(workout.getWorkoutId()));

        FragmentManager fm = getParentFragmentManager();
        Fragment fragment = new WorkoutFragment();
        fragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.toString())
                .commit();
    }

    public boolean checkGradeWithinGoal(Workout workout, UserAccount user) {
        int startLevel = 0, endLevel = 0;
        int currentLevel = user.mCLevel;
        int goalLevel = user.mGLevel;
        boolean withinGoal = false;

        if (currentLevel == 1) {
            startLevel = 0;
        } else if (currentLevel == 2) {
            startLevel = 3;
        } else if (currentLevel == 3) {
            startLevel = 6;
        } else if (currentLevel == 4) {
            startLevel = 10;
        } else if (currentLevel == 5) {
            startLevel = 13;
        }

        if (goalLevel == 1) {
            endLevel = 2;
        } else if (goalLevel == 2) {
            endLevel = 5;
        } else if (goalLevel == 3) {
            endLevel = 9;
        } else if (goalLevel == 4) {
            endLevel = 12;
        } else if (goalLevel == 5) {
            endLevel = 17;
        }

        if (workout.getGrade() >= startLevel && workout.getGrade() <= endLevel) {
            withinGoal = true;
        }

        return withinGoal;
    }
}
