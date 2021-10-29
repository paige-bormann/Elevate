package com.example.elevate.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.UserAccount;

import timber.log.Timber;

public class QuestionTwoFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
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

        View v = inflater.inflate(R.layout.fragment_question_two, container, false);

        mRadioGroup = v.findViewById(R.id.answer_two_radiogroup);
        if (mRadioGroup != null) {
            mRadioGroup.setOnCheckedChangeListener(this);
        }
        mNextButton = v.findViewById(R.id.next_button_two);
        if (mNextButton != null) {
            mNextButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getParentFragmentManager();
        Fragment fragment = new QuestionThreeFragment();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.toString())
                .commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        UserAccount user = mElevateViewModel.getCurrentUser();
        if (checkedId == R.id.power_radiobutton) {
            //user.setPreferredType("Power");
            //mElevateViewMode.update(user);
        } else if (checkedId == R.id.technique_radiobutton) {
            //user.setPreferredType("Technique");
            //mElevateViewModel.update(user);
        }
    }
}
