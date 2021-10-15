package com.example.elevate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import timber.log.Timber;

public class SignInFragment extends Fragment implements View.OnClickListener {
    private Button mSignInButton;
    private Button mSignUpButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate() called");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView() called");

        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);

        mSignInButton = v.findViewById(R.id.sign_in_button);
        if (mSignInButton != null) {
            mSignInButton.setOnClickListener(this);
        }
        mSignUpButton = v.findViewById(R.id.sign_up_button);
        if (mSignUpButton != null) {
            mSignUpButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.sign_in_button) {
            FragmentManager fm = getParentFragmentManager();
            Fragment fragment = new LoginFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
        } else if (viewId == R.id.sign_up_button) {
            FragmentManager fm = getParentFragmentManager();
            Fragment fragment = new SignUpFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
        }
    }
}