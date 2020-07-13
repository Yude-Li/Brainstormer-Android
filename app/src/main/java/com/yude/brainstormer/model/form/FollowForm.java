package com.yude.brainstormer.model.form;

import com.yude.brainstormer.callback.api.ApiJsonForm;

import org.json.JSONObject;

public class FollowForm implements ApiJsonForm {
    private String username;
    private String followUsername;

    public FollowForm() {
    }

    public FollowForm(String username, String followUsername) {
        this.username = username;
        this.followUsername = followUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFollowUsername() {
        return followUsername;
    }

    public void setFollowUsername(String followUsername) {
        this.followUsername = followUsername;
    }

    @Override
    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", getUsername());
            jsonObject.put("followUsername", getFollowUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String getURL() {
        return "http://10.0.2.2:8080/follow";
    }

    @Override
    public String getResult() {
        return null;
    }
}
