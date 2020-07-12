package com.yude.brainstormer.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yude.brainstormer.R;
import com.yude.brainstormer.callback.api.ApiCallback;
import com.yude.brainstormer.model.form.RegisterBrainForm;
import com.yude.brainstormer.rest.PostTaskJson;
import com.yude.brainstormer.view.callback.LoginCallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RegisterFragment extends Fragment implements ApiCallback<RegisterBrainForm> {

    private EditText emailET;
    private EditText usernameET;
    private EditText passwordET;
    private EditText firstNameET;
    private EditText lastNameET;

    private LoginCallback callback;
    private Context context;

    public RegisterFragment() {
        // Required empty public constructor
    }

    private RegisterFragment getFragment() {
        return this;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        callback = (LoginCallback) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailET = view.findViewById(R.id.editText_register_email);
        usernameET = view.findViewById(R.id.editText_register_username);
        passwordET = view.findViewById(R.id.editText_register_pwd);
        firstNameET = view.findViewById(R.id.editText_register_firstName);
        lastNameET = view.findViewById(R.id.editText_register_lastName);

        Button btn = view.findViewById(R.id.button_register_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterBrainForm registerBrainForm = new RegisterBrainForm();
                registerBrainForm.setEmail(emailET.getText().toString());
                registerBrainForm.setUsername(usernameET.getText().toString());
                registerBrainForm.setPassword(passwordET.getText().toString());
                registerBrainForm.setFirstName(firstNameET.getText().toString());
                registerBrainForm.setLastName(lastNameET.getText().toString());

                new PostTaskJson<>(RegisterBrainForm.class, getFragment()).execute(registerBrainForm);
            }
        });
    }

    @Override
    public void getResult(HttpStatus httpStatus, Object data) {

    }

    @Override
    public void postResult(ResponseEntity<RegisterBrainForm> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Toast.makeText(
                    this.getContext(),
                    responseEntity.getBody().getUsername() + " Register successfully",
                    Toast.LENGTH_LONG
            ).show();
            callback.registerResultCallback();
        }
        else {
            Toast.makeText(
                    this.getContext(),
                    responseEntity.getBody().getUsername() + " Register fail",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

//    @Override
//    public void postResult(HttpStatus httpStatus, String result) {
//        if (httpStatus == HttpStatus.OK) {
//            Toast.makeText(
//                    this.getContext(),
//                    result + " Register successfully",
//                    Toast.LENGTH_LONG
//            ).show();
//            callback.registerResultCallback();
//        }
//        else {
//            Toast.makeText(
//                    this.getContext(),
//                    result + " Register fail",
//                    Toast.LENGTH_LONG
//            ).show();
//        }
//    }
}