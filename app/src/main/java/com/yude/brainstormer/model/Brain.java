package com.yude.brainstormer.model;

import java.util.List;

public class Brain {

    private long id;
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<Brain> follows;
//    private List<Idea> ideas;

    public Brain() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Brain> getFollows() {
        return follows;
    }

    public void setFollows(List<Brain> follows) {
        this.follows = follows;
    }

    public void addFollows(Brain newFollow) {
        this.follows.add(newFollow);
    }

    public void delFollows(Brain newFollow) {
        this.follows.remove(newFollow);
    }

//    public List<Idea> getIdeas() {
//        return ideas;
//    }
//
//    public void setIdeas(List<Idea> ideas) {
//        this.ideas = ideas;
//    }
//
//    public void addIdeas(Idea newIdeas) {
//        this.ideas.add(newIdeas);
//    }
//
//    public void delIdeas(Idea newIdeas) {
//        this.ideas.remove(newIdeas);
//    }
}
