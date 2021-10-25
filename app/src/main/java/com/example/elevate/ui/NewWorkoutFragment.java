package com.example.elevate.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.Workout;

import java.util.List;

import timber.log.Timber;

public class NewWorkoutFragment extends Fragment implements View.OnClickListener {
    private ElevateViewModel mElevateViewModel;
    private Button mCreateWorkoutButton;
    private EditText mNameEditText;
    private EditText mStyleEditText;
    private EditText mGradeEditText;
    private EditText mTutorialEditText;

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

        View v = inflater.inflate(R.layout.fragment_new_workout, container, false);

        LiveData<List<Workout>> workoutListData = mElevateViewModel.getAllWorkouts();
        List<Workout> workoutList = workoutListData.getValue();

        mNameEditText = v.findViewById(R.id.name_edittext);
        mStyleEditText = v.findViewById(R.id.style_edittext);
        mGradeEditText = v.findViewById(R.id.grade_edittext);
        mTutorialEditText = v.findViewById(R.id.tutorial_edittext);

        mCreateWorkoutButton = v.findViewById(R.id.create_workout_button);
        if (mCreateWorkoutButton != null) {
            mCreateWorkoutButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        final String name = mNameEditText.getText().toString();
        final String style = mStyleEditText.getText().toString();
        final int grade = Integer.parseInt(mGradeEditText.getText().toString());
        final String tutorial = mTutorialEditText.getText().toString();

        FragmentActivity activity = requireActivity();
        Workout workout = new Workout(name, style, grade, tutorial);
        mElevateViewModel.insert(workout);
        Toast.makeText(activity.getApplicationContext(), "New Workout added", Toast.LENGTH_SHORT).show();
    }
}
