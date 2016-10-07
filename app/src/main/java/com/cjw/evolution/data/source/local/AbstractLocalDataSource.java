package com.cjw.evolution.data.source.local;

import com.cjw.evolution.data.DaoInjection;
import com.cjw.evolution.data.source.local.db.DaoSession;

/**
 * Created by CJW on 2016/7/19.
 */
public abstract class AbstractLocalDataSource {

    protected DaoSession daoSession;

    public AbstractLocalDataSource() {
        daoSession = DaoInjection.getInstance().getDaoSession();
    }
}
