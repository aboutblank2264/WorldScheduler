package com.aboutblank.world_clock.ui.components;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.aboutblank.world_clock.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagDialog extends DialogFragment {

    @BindView(R.id.dialog_edit_text)
    EditText tagEditText;

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

        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setView(view)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        onPositiveClick();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        listener.onNegativeClick(TagDialog.this);
                    }
                })
                .setTitle("Add message to alarm");

        tagEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onPositiveClick();
                }
                return true;
            }
        });

        return builder.create();
    }

    private void onPositiveClick() {
        listener.onPositiveClick(this, String.valueOf(tagEditText.getText()));
    }

    public interface TagDialogListener {
        void onPositiveClick(TagDialog dialog, String message);

        void onNegativeClick(TagDialog dialog);
    }
}
