package com.yude.brainstormer.model.form;

import com.yude.brainstormer.callback.api.ApiJsonForm;
import com.yude.brainstormer.model.Brain;
import com.yude.brainstormer.model.Idea;

import org.json.JSONObject;

public class IdeaDeleteForm implements ApiJsonForm {

    private long id;
    private String title;
    private String context;
    private String content;
    private String authorName;

    public IdeaDeleteForm() {
    }

    public IdeaDeleteForm(String title, String context, String content) {
        this.title = title;
        this.context = context;
        this.content = content;
    }

    public IdeaDeleteForm(Idea idea) {
        this.id = idea.getId();
        this.title = idea.getTitle();
        this.context = idea.getContext();
        this.content = idea.getContent();
        this.authorName = idea.getAuthor().getUsername();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("authorName", getAuthorName());
            jsonObject.put("id", getId());
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
        return "http://10.0.2.2:8080/idea/delete";
    }

    @Override
    public String getResult() {
        return null;
    }
}
