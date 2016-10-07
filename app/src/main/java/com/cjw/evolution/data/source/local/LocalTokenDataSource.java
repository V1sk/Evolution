package com.cjw.evolution.data.source.local;

import com.cjw.evolution.data.model.Token;
import com.cjw.evolution.data.source.TokenContract;
import com.cjw.evolution.data.source.local.db.TokenDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by CJW on 2016/10/1.
 */

public class LocalTokenDataSource extends AbstractLocalDataSource implements TokenContract.Local {


    private TokenDao tokenDao;

    public LocalTokenDataSource() {
        super();
        tokenDao = daoSession.getTokenDao();
    }

    @Override
    public Token token() {
        QueryBuilder<Token> queryBuilder = tokenDao.queryBuilder();
        List<Token> tokens = queryBuilder.limit(1).list();
        return tokens != null && !tokens.isEmpty() ? tokens.get(0) : null;
    }

    @Override
    public boolean save(final Token token) {
        tokenDao.deleteAll();
        tokenDao.insertOrReplace(token);
        return false;
    }

    @Override
    public boolean deleteAll() {
        tokenDao.deleteAll();
        return true;
    }
}
