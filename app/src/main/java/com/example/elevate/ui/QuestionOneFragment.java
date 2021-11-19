package com.example.elevate.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.UserAccount;

import java.util.List;

import timber.log.Timber;

public class QuestionOneFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private ElevateViewModel mElevateViewModel;
    private RadioGroup mRadioGroup;
    private Button mNextButton;

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
                actionBar.setSubtitle(getResources().getString(R.string.skill_survey));
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
            v = inflater.inflate(R.layout.fragment_question_one_land, container, false);
        } else {
            v = inflater.inflate(R.layout.fragment_question_one, container, false);
        }

        mRadioGroup = v.findViewById(R.id.answer_one_radiogroup);
        if (mRadioGroup != null) {
            mRadioGroup.setOnCheckedChangeListener(this);
        }
        mNextButton = v.findViewById(R.id.next_button);
        if (mNextButton != null) {
            mNextButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getParentFragmentManager();
        Fragment fragment = new QuestionTwoFragment();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.toString())
                .commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        UserAccount user = mElevateViewModel.getCurrentUser();
        if (checkedId == R.id.beginner_radiobutton) {
            user.setCLevel(1);
            mElevateViewModel.update(user);
        } else if (checkedId == R.id.intermediate_radiobutton) {
            user.setCLevel(2);
            mElevateViewModel.update(user);
        } else if (checkedId == R.id.advanced_radiobutton) {
            user.setCLevel(3);
            mElevateViewModel.update(user);
        } else if (checkedId == R.id.elite_radiobutton) {
            user.setCLevel(4);
            mElevateViewModel.update(user);
        } else if (checkedId == R.id.professional_radiobutton) {
            user.setCLevel(5);
            mElevateViewModel.update(user);
        }
    }
}
