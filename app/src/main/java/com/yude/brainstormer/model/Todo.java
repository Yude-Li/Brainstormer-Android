package com.yude.brainstormer.model;

public class Todo {

    private Idea idea;
    private boolean markAsDone;

    public Todo() {
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public boolean isMarkAsDone() {
        return markAsDone;
    }

    public void setMarkAsDone(boolean markAsDone) {
        this.markAsDone = markAsDone;
    }
}
