package com.example.elevate.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.Workout;

import timber.log.Timber;

public class WorkoutFragment extends Fragment implements View.OnClickListener {
    private ElevateViewModel mElevateViewModel;
    private TextView mWorkoutTextView;
    private TextView mStyleTextView;
    private TextView mDescriptionTextView;
    private Button mTutorialButton;
    private Button mCompleteWorkoutButton;
    private Workout mWorkout;

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
                actionBar.setSubtitle(getResources().getString(R.string.workout));
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
            v = inflater.inflate(R.layout.fragment_workout_land, container, false);
        } else {
            v = inflater.inflate(R.layout.fragment_workout, container, false);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int id = Integer.parseInt(bundle.get("id").toString());
            mWorkout = mElevateViewModel.getWorkout(id);

            mWorkoutTextView = v.findViewById(R.id.workout_textview);
            mStyleTextView = v.findViewById(R.id.style_display_textview);
            mDescriptionTextView=v.findViewById(R.id.Desc_textView);

            String grade = "V" + String.valueOf(mWorkout.getGrade());
            mWorkoutTextView.setText(mWorkout.getName() + " (" + grade + ")");
            mStyleTextView.setText(mWorkout.getStyle());
            mDescriptionTextView.setText(mWorkout.getDescription());
        }

        mTutorialButton = v.findViewById(R.id.tutorial_button);
        if (mTutorialButton != null) {
            mTutorialButton.setOnClickListener(this);
        }

        mCompleteWorkoutButton = v.findViewById(R.id.complete_button);
        if (mWorkout.getCompleted()) {
            mCompleteWorkoutButton.setText(getResources().getString(R.string.button_incomplete_workout));
        }
        if (mCompleteWorkoutButton != null) {
            mCompleteWorkoutButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        Activity activity = requireActivity();
        final int viewId = view.getId();
        //FragmentActivity activity = requireActivity();

        if (viewId == R.id.tutorial_button) {
            // Open tutorial fragment
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(mWorkout.getWorkoutId()));

            FragmentManager fm = getParentFragmentManager();
            Fragment fragment = new TutorialFragment();
            fragment.setArguments(bundle);
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
        } else if (viewId == R.id.complete_button) {
            // toggle workout complete
            if (mWorkout.getCompleted()) {
                mWorkout.setCompleted(false);
                mElevateViewModel.update(mWorkout);
                Toast.makeText(activity.getApplicationContext(), "Workout uncompleted.", Toast.LENGTH_SHORT).show();
            } else {
                mWorkout.setCompleted(true);
                mElevateViewModel.update(mWorkout);
                Toast.makeText(activity.getApplicationContext(), "Workout completed.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
