package com.yude.brainstormer.model.form;

import com.yude.brainstormer.callback.api.ApiJsonForm;

import org.json.JSONObject;

public class TodoForm implements ApiJsonForm {

    private long id;
    private long ideaId;
    private String ideaTitle;
    private String citeUsername;
    private boolean markAsDone;
    private boolean isCiting;

    public TodoForm() {
    }

    public TodoForm(long id, long ideaId, String ideaTitle, String citeUsername, boolean markAsDone, boolean isCiting) {
        this.id = id;
        this.ideaId = ideaId;
        this.ideaTitle = ideaTitle;
        this.citeUsername = citeUsername;
        this.markAsDone = markAsDone;
        this.isCiting = isCiting;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(long ideaId) {
        this.ideaId = ideaId;
    }

    public String getIdeaTitle() {
        return ideaTitle;
    }

    public void setIdeaTitle(String ideaTitle) {
        this.ideaTitle = ideaTitle;
    }

    public String getCiteUsername() {
        return citeUsername;
    }

    public void setCiteUsername(String citeUsername) {
        this.citeUsername = citeUsername;
    }

    public boolean isMarkAsDone() {
        return markAsDone;
    }

    public void setMarkAsDone(boolean markAsDone) {
        this.markAsDone = markAsDone;
    }

    public boolean isCiting() {
        return isCiting;
    }

    public void setCiting(boolean citing) {
        isCiting = citing;
    }

    @Override
    public JSONObject getJson() {
        return null;
    }

    @Override
    public String getURL() {
        return null;
    }

    @Override
    public String getResult() {
        return null;
    }
}
