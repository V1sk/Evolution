package com.cjw.evolution.data.source.local.db;

import com.cjw.evolution.data.model.Token;
import com.cjw.evolution.data.model.User;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig tokenDaoConfig;

    private final UserDao userDao;
    private final TokenDao tokenDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        tokenDaoConfig = daoConfigMap.get(TokenDao.class).clone();
        tokenDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        tokenDao = new TokenDao(tokenDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(Token.class, tokenDao);
    }
    
    public void clear() {
        userDaoConfig.clearIdentityScope();
        tokenDaoConfig.clearIdentityScope();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public TokenDao getTokenDao() {
        return tokenDao;
    }

}
