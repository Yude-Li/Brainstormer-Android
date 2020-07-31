package com.yude.brainstormer.model.form;

import com.yude.brainstormer.callback.api.ApiJsonForm;

import org.json.JSONObject;

public class AssignIdeaToBrainForm implements ApiJsonForm {
    private long id;
    private String username;

    public AssignIdeaToBrainForm(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
            jsonObject.put("id", getId());
            jsonObject.put("username", getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String getURL() {
        return "http://10.0.2.2:8080/brainpost";
    }

    @Override
    public String getResult() {
        return null;
    }
}
