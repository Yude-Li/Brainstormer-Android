package com.yude.brainstormer.model.form;

import com.yude.brainstormer.callback.api.ApiJsonForm;

import org.json.JSONObject;

public class CiteForm implements ApiJsonForm {

    private long citeIdeaId;
    private String citeUsername;
    private String citeTitle;
    private String citeContext;
    private String citeContent;
    private boolean isCiting;

    public CiteForm() {
    }

    public CiteForm(long citeIdeaId, String citeUsername, String citeTitle, String citeContext, String citeContent, boolean isCiting) {
        this.citeIdeaId = citeIdeaId;
        this.citeUsername = citeUsername;
        this.citeTitle = citeTitle;
        this.citeContext = citeContext;
        this.citeContent = citeContent;
        this.isCiting = isCiting;
    }

    public long getCiteIdeaId() {
        return citeIdeaId;
    }

    public void setCiteIdeaId(long citeIdeaId) {
        this.citeIdeaId = citeIdeaId;
    }

    public String getCiteUsername() {
        return citeUsername;
    }

    public void setCiteUsername(String citeUsername) {
        this.citeUsername = citeUsername;
    }

    public String getCiteTitle() {
        return citeTitle;
    }

    public void setCiteTitle(String citeTitle) {
        this.citeTitle = citeTitle;
    }

    public String getCiteContext() {
        return citeContext;
    }

    public void setCiteContext(String citeContext) {
        this.citeContext = citeContext;
    }

    public String getCiteContent() {
        return citeContent;
    }

    public void setCiteContent(String citeContent) {
        this.citeContent = citeContent;
    }

    public boolean isCiting() {
        return isCiting;
    }

    public void setCiting(boolean citing) {
        isCiting = citing;
    }

    @Override
    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("citeIdeaId", getCiteIdeaId());
            jsonObject.put("citeUsername", getCiteUsername());
            jsonObject.put("citeTitle", getCiteTitle());
            jsonObject.put("citeContext", getCiteContext());
            jsonObject.put("citeContent", getCiteContent());
            jsonObject.put("isCiting", isCiting());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String getURL() {
        return "http://10.0.2.2:8080/cite";
    }

    @Override
    public String getResult() {
        return null;
    }
}
