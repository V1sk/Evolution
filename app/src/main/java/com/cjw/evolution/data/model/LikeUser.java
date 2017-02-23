package com.cjw.evolution.data.model;

/**
 * Created by chenjianwei on 2017/2/23.
 */

public class LikeUser {

    private long id;
    private String created_at;
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
