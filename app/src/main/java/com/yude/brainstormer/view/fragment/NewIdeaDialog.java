package com.yude.brainstormer.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.yude.brainstormer.R;
import com.yude.brainstormer.view.callback.NewIdeaListener;

public class NewIdeaDialog extends DialogFragment {

    private EditText titleET;
    private EditText contextET;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();

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
        View view = inflater.inflate(R.layout.ideapost_dialog, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleET = view.findViewById(R.id.editText_ideaDialog_Title);
        contextET = view.findViewById(R.id.editText_ideaDialog_context);
        contentET = view.findViewById(R.id.editText_ideaDialog_content);

        Button btn = view.findViewById(R.id.button_ideaDialog_post);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleET.getText().toString();
                String context = contextET.getText().toString();
                String content = contentET.getText().toString();

                if (title.equals("") || context.equals("") || content.equals("")) {
                    showErrorMsg();
                }
                else {
                    listener.newIdeaInfo(title, context, content);
                    dismiss();
                }
            }
        });

        Button btnCancel = view.findViewById(R.id.button_ideaDialog_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void showErrorMsg() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Empty Input");
        alertDialog.setMessage("Please fill the blanks.");
        alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
