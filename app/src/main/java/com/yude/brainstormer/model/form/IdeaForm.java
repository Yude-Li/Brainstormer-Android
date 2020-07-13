package com.yude.brainstormer.model.form;

import com.yude.brainstormer.callback.api.ApiJsonForm;

import org.json.JSONObject;

public class IdeaForm implements ApiJsonForm {
    private String username;
    private String title;
    private String context;
    private String content;

    public IdeaForm() {
    }

    public IdeaForm(String username, String title, String context, String content) {
        this.username = username;
        this.title = title;
        this.context = context;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", getUsername());
            jsonObject.put("title", getTitle());
            jsonObject.put("context", getContext());
            jsonObject.put("content", getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String getURL() {
        return "http://10.0.2.2:8080/idea/new";
    }

    @Override
    public String getResult() {
        return null;
    }
}
