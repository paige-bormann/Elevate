package com.example.elevate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import timber.log.Timber;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate() called");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView() called");

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mUsernameEditText = v.findViewById(R.id.username_text);
        mPasswordEditText = v.findViewById(R.id.password_text);
        mLoginButton = v.findViewById(R.id.create_account_button);
        if (mLoginButton != null) {
            mLoginButton.setOnClickListener(this);
        }


        return v;
    }

    private void checkLogin() {
        final String username = mUsernameEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        // do stuff to check login
    }

    @Override
    public void onClick(View v) {
        checkLogin();
    }
}