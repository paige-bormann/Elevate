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
import com.example.elevate.model.Workout;
import com.example.elevate.model.WorkoutAdapter;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ClimbingPlanFragment extends Fragment implements WorkoutAdapter.OnWorkoutListener {
    private ElevateViewModel mElevateViewModel;
    private List<Workout> mWorkouts = new ArrayList<>();

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

        mElevateViewModel.getAllWorkouts().observe((LifecycleOwner) activity, new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workoutList) {
                mWorkouts = workoutList;
                adapter.setWorkouts(workoutList);

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
}
