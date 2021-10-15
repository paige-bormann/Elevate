package com.example.elevate;

import androidx.fragment.app.Fragment;

public class SignUpActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() { return new SignUpFragment(); }
}