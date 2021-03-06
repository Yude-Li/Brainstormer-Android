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
import com.yude.brainstormer.dao.DaoFactory;
import com.yude.brainstormer.dao.DataDao;
import com.yude.brainstormer.model.Brain;
import com.yude.brainstormer.model.form.LoginBrainForm;
import com.yude.brainstormer.model.form.RegisterBrainForm;
import com.yude.brainstormer.rest.PostTaskJson;
import com.yude.brainstormer.view.callback.LoginCallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LoginFragment extends Fragment implements ApiCallback<Brain>  {

    private EditText emailET;
    private EditText pwdET;

    private LoginCallback callback;
    private Context context;
    private DataDao dao;

    public LoginFragment() {
        // Required empty public constructor
    }

    private LoginFragment getFragment() {
        return this;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        dao = DaoFactory.getDataDao();
        callback = (LoginCallback) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailET = view.findViewById(R.id.editText_login_email);
        pwdET = view.findViewById(R.id.editText_login_pwd);

        Button signinBtn = view.findViewById(R.id.button_login_signIn);
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginBrainForm loginBrainForm = new LoginBrainForm();
                loginBrainForm.setEmail(emailET.getText().toString());
                loginBrainForm.setPassword(pwdET.getText().toString());

                new PostTaskJson<LoginBrainForm, Brain>(Brain.class, getFragment()).execute(loginBrainForm);
            }
        });

        Button signupBtn = view.findViewById(R.id.button_login_signUp);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.signUpBtnOnClickCallback();
            }
        });
    }

    // region Interface callback
    @Override
    public void postResult(ResponseEntity<Brain> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody().getUsername() != null) {
            Toast.makeText(
                    this.getContext(),
                    responseEntity.getBody().getUsername() + " Login successfully",
                    Toast.LENGTH_LONG
            ).show();

            dao.setCurrentBrain(responseEntity.getBody());
            callback.signInResultCallback();
        }
        else {
            Toast.makeText(
                    this.getContext(),
                    "Error: " + responseEntity.getStatusCode().toString(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
    // endregion
}