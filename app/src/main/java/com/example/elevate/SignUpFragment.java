package com.example.elevate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import timber.log.Timber;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private Button mCreateAccountButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate() called");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView() called");

        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mUsernameEditText = v.findViewById(R.id.username_text);
        mPasswordEditText = v.findViewById(R.id.password_text);
        mConfirmPasswordEditText = v.findViewById(R.id.confirm_password_text);
        mCreateAccountButton = v.findViewById(R.id.create_account_button);
        if (mCreateAccountButton != null) {
            mCreateAccountButton.setOnClickListener(this);
        }

        return v;
    }

    private void createAccount() {
        // FragmentActivity activity = requireActivity();
        final String username = mUsernameEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();
        final String confirm = mConfirmPasswordEditText.getText().toString();

        // do stuff to create account
    }

    @Override
    public void onClick(View v) {
        createAccount();
    }
}