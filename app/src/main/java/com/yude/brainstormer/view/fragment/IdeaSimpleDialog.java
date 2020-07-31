package com.yude.brainstormer.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.yude.brainstormer.R;
import com.yude.brainstormer.model.Idea;

public class IdeaSimpleDialog extends DialogFragment {

    private TextView titleET;
    private TextView authorET;
    private TextView contextET;
    private TextView contentET;

    private static Context mContext;
    private static Idea mIdea;

    public static IdeaSimpleDialog newInstance(Idea idea, Context context) {
        mContext = context;
        IdeaSimpleDialog dialog = new IdeaSimpleDialog();
        mIdea = idea;
        return dialog;
    }

    @Override
    public void dismiss() {
        if (getDialog() != null) {
            super.dismiss();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
//        getDialog().setCanceledOnTouchOutside(true);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int winWidth = displaymetrics.widthPixels;
        int winHeight = displaymetrics.heightPixels;

        Log.d("Window", "Width: " + String.valueOf(winWidth));
        Log.d("Window", "Height: " + String.valueOf(winHeight));

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = winWidth - 100;
            int height = winHeight - 100;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.idea_simple_dialog, null);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setCancelable(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleET = view.findViewById(R.id.editText_simpleDialog_Title);
        authorET = view.findViewById(R.id.editText_simpleDialog_author);
        contextET = view.findViewById(R.id.editText_simpleDialog_context);
        contentET = view.findViewById(R.id.editText_simpleDialog_content);

        titleET.setText(mIdea.getTitle());
        authorET.setText(mIdea.getAuthor().getUsername());
        contextET.setText(mIdea.getContext());
        contentET.setText(mIdea.getContent());

        if(mIdea.getOriginalIdea() != null) {
            contextET.setTextColor(Color.BLUE);
            contextET.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IdeaSimpleDialog ideaSimpleDialog = IdeaSimpleDialog.newInstance(mIdea.getOriginalIdea(), mContext);
                    ideaSimpleDialog.show(getFragmentManager(), "");
                }
            });
        }

        Button btnCancel = view.findViewById(R.id.button_simpleDialog_close);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
