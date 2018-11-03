package com.kpgn.optimizecontacts.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpgn.optimizecontacts.R;

public class ProgressDialogFragment extends DialogFragment {

    private static final String DIALOG_TAG = "ProgressDialog";
    private static final String TAG = ProgressDialogFragment.class.getSimpleName();

    private static ProgressDialogFragment progressDialogFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.AppTheme_Dialog);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() != null) {
            return new Dialog(getActivity(), getTheme()) {
                @Override
                public void onBackPressed() {
                    // do nothing
                }
            };
        }
        return null;
    }

    public static void showProgressDialog(final Context context) {
        final FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        progressDialogFragment = new ProgressDialogFragment();
        try {
            progressDialogFragment.show(ft, DIALOG_TAG);
        } catch (IllegalStateException ise) {
            Log.e(TAG, ise.getLocalizedMessage());
        }
    }

    public static void hideProgressDialog() {
        if (progressDialogFragment != null) {
            progressDialogFragment.dismiss();
        }
    }
}