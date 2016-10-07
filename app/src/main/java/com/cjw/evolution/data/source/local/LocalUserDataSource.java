package com.cjw.evolution.data.source.local;

import com.cjw.evolution.data.model.User;
import com.cjw.evolution.data.source.UserContract;
import com.cjw.evolution.data.source.local.db.UserDao;

import java.util.List;

import rx.Observable;

/**
 * Created by CJW on 2016/10/1.
 */

public class LocalUserDataSource extends AbstractLocalDataSource implements UserContract.Local {

    private UserDao userDao;

    public LocalUserDataSource() {
        userDao = daoSession.getUserDao();
    }

    @Override
    public Observable<User> user() {
        return Observable.just(_user());
    }

    @Override
    public User _user() {
        List<User> users = userDao.queryBuilder().limit(1).list();
        return users != null && !users.isEmpty() ? users.get(0) : null;
    }

    @Override
    public boolean save(final User user) {
        userDao.insertOrReplace(user);
        return true;
    }

    @Override
    public int deleteAll() {
        userDao.deleteAll();
        return 0;
    }
}
