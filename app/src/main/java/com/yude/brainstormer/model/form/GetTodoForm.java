package com.yude.brainstormer.model.form;

import com.yude.brainstormer.callback.api.ApiJsonForm;

import org.json.JSONObject;

public class GetTodoForm implements ApiJsonForm {

    private String username;

    public GetTodoForm(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String getURL() {
        return "http://10.0.2.2:8080/todo/all";
    }

    @Override
    public String getResult() {
        return null;
    }
}
