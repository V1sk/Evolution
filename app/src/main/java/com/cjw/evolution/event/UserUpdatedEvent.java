package com.cjw.evolution.event;

import com.cjw.evolution.data.model.User;

/**
 * Created by CJW on 2016/10/1.
 */

public class UserUpdatedEvent {

    public User user;

    public UserUpdatedEvent(User user) {
        this.user = user;
    }
}
