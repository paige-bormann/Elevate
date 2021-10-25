package com.example.elevate.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.preference.PreferenceManager;

import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.StringUtils;
import com.example.elevate.model.UserAccount;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private ElevateViewModel mElevateViewModel;

    private final static String OPT_NAME = "name";
    private final static String OPT_PASS = "password";

    private final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate() called");

        Activity activity = requireActivity();
        mElevateViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ElevateViewModel.class);

        // Here's a dummy observer object that indicates when the UserAccounts change in the database.
        mElevateViewModel.getAllNames().observe((LifecycleOwner) activity, new Observer<List<UserAccount>>() {
            @Override
            public void onChanged(List<UserAccount> userAccounts) {
                Timber.d(TAG, "The list of UserAccounts just changed; it has %s elements", userAccounts.size());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView() called");

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mUsernameEditText = v.findViewById(R.id.username_text);
        mPasswordEditText = v.findViewById(R.id.password_text);
        mLoginButton = v.findViewById(R.id.survey_button);
        if (mLoginButton != null) {
            mLoginButton.setOnClickListener(this);
        }


        return v;
    }

    private void checkLogin() {
        final String username = mUsernameEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        // Check if given username/password corresponds to existing user account
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] sha256HashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String sha256HashStr = StringUtils.bytesToHex(sha256HashBytes);

            UserAccount userAccount = new UserAccount(username, sha256HashStr);
            boolean validUser = mElevateViewModel.containsUserAccount(userAccount);
            if (validUser) {
                // Open home page once logged in successfully
                FragmentManager fm = getParentFragmentManager();
                Fragment fragment = new HomeFragment();
                fm.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(fragment.toString())
                        .commit();
            } else {
                FragmentManager manager = getParentFragmentManager();
                LoginErrorDialogFragment fragment = new LoginErrorDialogFragment();
                fragment.show(manager, "login_error");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        checkLogin();
    }
}