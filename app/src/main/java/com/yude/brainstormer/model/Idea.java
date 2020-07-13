package com.yude.brainstormer.model;

public class Idea {
    private long id;
    private String title;
    private String context;
    private String content;
    private Brain author;

    public Idea() {
    }

    public Idea(String title, String context, String content) {
        this.title = title;
        this.context = context;
        this.content = content;
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

    public Brain getAuthor() {
        return author;
    }

    public void setAuthor(Brain author) {
        this.author = author;
    }
}
