package com.yude.brainstormer.model.form;

import com.yude.brainstormer.callback.api.ApiJsonForm;

import org.json.JSONObject;

public class LoginBrainForm implements ApiJsonForm {

    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public LoginBrainForm() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", getEmail());
            jsonObject.put("username", "");
            jsonObject.put("password", getPassword());
            jsonObject.put("firstName", "");
            jsonObject.put("lastName", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String getURL() {
        return "http://10.0.2.2:8080/brain/login";
    }

    @Override
    public String getResult() {
        return "Username = " + this.username;
    }
}
