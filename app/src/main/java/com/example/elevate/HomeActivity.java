package com.example.elevate;

import androidx.fragment.app.Fragment;

public class HomeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() { return new HomeFragment(); }
}