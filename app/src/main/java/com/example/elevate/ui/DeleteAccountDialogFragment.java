package com.example.elevate.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.elevate.R;
import com.example.elevate.model.ElevateViewModel;
import com.example.elevate.model.UserAccount;

/**
 * DialogFragment that gives user delete account confirm message.
 *
 * Source: https://developer.android.com/guide/topics/ui/dialogs
 *
 */
public class DeleteAccountDialogFragment extends DialogFragment {
    private ElevateViewModel mElevateViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = requireActivity();
        mElevateViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ElevateViewModel.class);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_account_confirm)
                .setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Activity activity = requireActivity();
                            UserAccount currentUser = mElevateViewModel.getCurrentUser();
                            mElevateViewModel.delete(currentUser);
                            Toast.makeText(activity.getApplicationContext(), "UserAccount deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, MainActivity.class));
                            activity.finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
