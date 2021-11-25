package com.example.elevate.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.R;
import com.example.elevate.StringUtils;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.UserAccount;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import timber.log.Timber;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private Button mCreateAccountButton;

    private ElevateViewModel mElevateViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate() called");
        Activity activity = requireActivity();
        mElevateViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ElevateViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView() called");

        View v;
        Activity activity = requireActivity();
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            v = inflater.inflate(R.layout.fragment_sign_up_land, container, false);
        } else {
            v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        }

        mUsernameEditText = v.findViewById(R.id.username_text);
        mPasswordEditText = v.findViewById(R.id.password_text);
        mConfirmPasswordEditText = v.findViewById(R.id.confirm_password_text);
        mCreateAccountButton = v.findViewById(R.id.survey_button);
        if (mCreateAccountButton != null) {
            mCreateAccountButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void createAccount() {
        FragmentActivity activity = requireActivity();
        final String username = mUsernameEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();
        final String confirm = mConfirmPasswordEditText.getText().toString();

        // If passwords match and given non-empty username/password, then create user account
        if (password.equals(confirm) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] sha256HashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                String sha256HashStr = StringUtils.bytesToHex(sha256HashBytes);

                UserAccount userAccount = new UserAccount(username, sha256HashStr);
                boolean userExists = mElevateViewModel.usernameAlreadyExists(username);
                if (userExists) {
                    Toast.makeText(activity, "Username already in use.", Toast.LENGTH_SHORT).show();
                } else {
                    // Create new UserAccount, add it to ViewModel
                    mElevateViewModel.insert(userAccount);
                    Toast.makeText(activity.getApplicationContext(), "New UserAccount added", Toast.LENGTH_SHORT).show();
                }
            } catch (NoSuchAlgorithmException e) {
                Toast.makeText(activity, "Error: No SHA-256 algorithm found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if ((username.equals("")) || (password.equals("")) || (confirm.equals(""))) {
            Toast.makeText(activity.getApplicationContext(), "Missing entry", Toast.LENGTH_SHORT).show();
        } else {
            Timber.e("An unknown account creation error occurred.");
            FragmentManager manager = getParentFragmentManager();
            AccountErrorDialogFragment fragment = new AccountErrorDialogFragment();
            fragment.show(manager, "account_error");
        }
    }

    @Override
    public void onClick(View v) {
        createAccount();
    }
}