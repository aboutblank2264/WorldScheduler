package com.aboutblank.worldscheduler.ui.components;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.ui.Keyboard;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagDialog extends DialogFragment {

    @BindView(R.id.dialog_edit_text)
    EditText tagText;

    private TagDialogListener listener;

    public static TagDialog newInstance(TagDialogListener listener) {
        TagDialog tagDialog = new TagDialog();
        tagDialog.listener = listener;

        return tagDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_tag, null);
        ButterKnife.bind(this, view);

        tagText.requestFocus();

        Keyboard.showKeyboard(this);

        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setView(view)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        onPositive();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        listener.onNegativeClick(TagDialog.this);
                    }
                });

        tagText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onPositive();
                }
                return true;
            }
        });

        tagText.clearFocus();

        return builder.create();
    }

    private void onPositive() {
        listener.onPositiveClick(this, String.valueOf(tagText.getText()));


        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tagText.getWindowToken(), 0);
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d("TEST", "onPause");

    }

    public interface TagDialogListener {
        void onPositiveClick(TagDialog dialog, String message);

        void onNegativeClick(TagDialog dialog);
    }
}
