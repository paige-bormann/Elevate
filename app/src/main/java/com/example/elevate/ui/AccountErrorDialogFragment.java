package com.example.elevate.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import com.example.elevate.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * DialogFragment that gives user account creation error.
 *
 * Source: adamcchampion's Tic-Tac-Toe application.
 *
 */
public class AccountErrorDialogFragment extends DialogFragment {
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireActivity())
                .setTitle(getResources().getString(R.string.warning))
                .setMessage(getResources().getString(R.string.passwords_match_error_text))
                .setPositiveButton(getResources().getString(R.string.try_again_text),
                        (dialog, which) -> {
                        }).create();
    }
}
