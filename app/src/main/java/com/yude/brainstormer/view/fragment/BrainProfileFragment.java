package com.yude.brainstormer.view.fragment;

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

import com.yude.brainstormer.R;
import com.yude.brainstormer.callback.api.ApiCallback;
import com.yude.brainstormer.model.Brain;
import com.yude.brainstormer.model.form.RegisterBrainForm;
import com.yude.brainstormer.rest.PostTaskJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BrainProfileFragment extends Fragment implements ApiCallback<Brain> {

    private EditText emailET;
    private EditText usernameET;
    private EditText passwordET;
    private EditText firstNameET;
    private EditText lastNameET;

    private BrainProfileFragment getFragment() {
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_brainprofile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        emailET = view.findViewById(R.id.editText_register_email);
        usernameET = view.findViewById(R.id.editText_register_username);
        passwordET = view.findViewById(R.id.editText_register_pwd);
        firstNameET = view.findViewById(R.id.editText_register_firstName);
        lastNameET = view.findViewById(R.id.editText_register_lastName);

        Button registerBtn = view.findViewById(R.id.button_register_save);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterBrainForm clientForm = new RegisterBrainForm();
                clientForm.setEmail(emailET.getText().toString());
                clientForm.setUsername(usernameET.getText().toString());
                clientForm.setPassword(passwordET.getText().toString());
                clientForm.setFirstName(firstNameET.getText().toString());
                clientForm.setLastName(lastNameET.getText().toString());

                new PostTaskJson<>(RegisterBrainForm.class, getFragment()).execute(clientForm);
            }
        });
    }

//    @Override
//    public void postResult(ResponseEntity<Brain> responseEntity) {
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            Brain brain = responseEntity.getBody();
//            Toast.makeText(
//                    this.getContext(),
//                    brain.getUsername() + " saved successfully",
//                    Toast.LENGTH_LONG
//            ).show();
//        }
//    }

    @Override
    public void getResult(HttpStatus httpStatus, Object data) {

    }

    @Override
    public void postResult(ResponseEntity<Brain> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Toast.makeText(
                    this.getContext(),
                    responseEntity.getBody().getUsername() + " saved successfully",
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

//    @Override
//    public void postResult(HttpStatus httpStatus, String result) {
//        if (httpStatus == HttpStatus.OK) {
//            Toast.makeText(
//                    this.getContext(),
//                    result + " saved successfully",
//                    Toast.LENGTH_LONG
//            ).show();
//        }
//        else {
//            Toast.makeText(
//                    this.getContext(),
//                    "Error code: " + httpStatus.toString(),
//                    Toast.LENGTH_LONG
//            ).show();
//        }
//    }
}
