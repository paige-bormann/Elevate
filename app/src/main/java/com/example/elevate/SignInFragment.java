package com.example.elevate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import timber.log.Timber;

public class SignInFragment extends Fragment {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Timber.d("onCreate() called");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_sign_in, container, false);

        Timber.d("onCreateView() called");

        final Button signInButton = v.findViewById(R.id.sign_in_button);
        if (signInButton != null) {
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Does nothing yet
                }
            });
        }

        return v;
    }
}