package com.aboutblank.worldscheduler.ui.components;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aboutblank.worldscheduler.R;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_tag, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        listener.onPositiveClick(TagDialog.this, String.valueOf(tagText.getText()));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        listener.onNegativeClick(TagDialog.this);
                    }
                });

        return builder.create();

    }

    public interface TagDialogListener {
        void onPositiveClick(TagDialog dialog, String message);

        void onNegativeClick(TagDialog dialog);
    }
}
