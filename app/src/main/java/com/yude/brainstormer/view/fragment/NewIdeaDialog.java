package com.yude.brainstormer.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.yude.brainstormer.R;
import com.yude.brainstormer.view.callback.NewIdeaListener;

public class NewIdeaDialog extends DialogFragment {

    private EditText titleET;
    private EditText contentET;

    private static Context mContext;
    private NewIdeaListener listener;

    public static NewIdeaDialog newInstance(String dialogTitle, Context context) {
        mContext = context;

        NewIdeaDialog dialog = new NewIdeaDialog();
        Bundle bundle = new Bundle();
        bundle.putString("dialogTitle", dialogTitle);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setListener(NewIdeaListener listener) {
        this.listener = listener;
    }

    @Override
    public void dismiss() {
        if (getDialog() != null) {
            super.dismiss();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ideapost_dialog, null);

        titleET = view.findViewById(R.id.editText_ideaDialog_Title);
        contentET = view.findViewById(R.id.editText_ideaDialog_content);

        Button btn = view.findViewById(R.id.button_ideaDialog_post);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
