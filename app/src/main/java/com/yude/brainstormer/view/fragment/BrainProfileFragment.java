package com.yude.brainstormer.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yude.brainstormer.HomeActivity;
import com.yude.brainstormer.MainActivity;
import com.yude.brainstormer.R;
import com.yude.brainstormer.callback.api.ApiCallback;
import com.yude.brainstormer.dao.DaoFactory;
import com.yude.brainstormer.dao.DataDao;
import com.yude.brainstormer.model.Brain;
import com.yude.brainstormer.model.form.LoginBrainForm;
import com.yude.brainstormer.model.form.RegisterBrainForm;
import com.yude.brainstormer.model.form.UpdateBrainForm;
import com.yude.brainstormer.rest.PostTaskJson;
import com.yude.brainstormer.view.callback.LoginCallback;
import com.yude.brainstormer.view.callback.SignOutCallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BrainProfileFragment extends Fragment implements ApiCallback<Brain> {

    private EditText emailET;
    private EditText usernameET;
    private EditText passwordET;
    private EditText firstNameET;
    private EditText lastNameET;

    private Context context;
    private DataDao dao;

    private BrainProfileFragment getFragment() {
        return this;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        dao = DaoFactory.getDataDao();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_brainprofile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        emailET = view.findViewById(R.id.editText_brainProfile_email);
        usernameET = view.findViewById(R.id.editText_brainProfile_username);
        passwordET = view.findViewById(R.id.editText_brainProfile_pwd);
        firstNameET = view.findViewById(R.id.editText_brainProfile_firstName);
        lastNameET = view.findViewById(R.id.editText_brainProfile_lastName);

        emailET.setText(dao.getCurrentBrain().getEmail());
        usernameET.setText(dao.getCurrentBrain().getUsername());
        passwordET.setText(dao.getCurrentBrain().getPassword());
        firstNameET.setText(dao.getCurrentBrain().getFirstName());
        lastNameET.setText(dao.getCurrentBrain().getLastName());

        emailET.setEnabled(false);
        usernameET.setEnabled(false);

        Button saveBtn = view.findViewById(R.id.button_brainProfile_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateBrainForm updateBrainForm = new UpdateBrainForm();
                updateBrainForm.setEmail(emailET.getText().toString());
                updateBrainForm.setUsername(usernameET.getText().toString());
                updateBrainForm.setPassword(passwordET.getText().toString());
                updateBrainForm.setFirstName(firstNameET.getText().toString());
                updateBrainForm.setLastName(lastNameET.getText().toString());

                new PostTaskJson<UpdateBrainForm, Brain>(Brain.class, getFragment()).execute(updateBrainForm);
            }
        });

        Button signoutBtn = view.findViewById(R.id.button_brainProfile_signout);
        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callback.signOutCallback();
                signOutCallback();
            }
        });
    }

    public void signOutCallback() {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    // region Interface callback
    @Override
    public void postResult(ResponseEntity<Brain> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Toast.makeText(
                    this.getContext(),
                    responseEntity.getBody().getUsername() + " update successfully",
                    Toast.LENGTH_LONG
            ).show();
        }
        else {
            Toast.makeText(
                    this.getContext(),
                    "Error code: " + responseEntity.getStatusCode().toString(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
    // endregion
}
