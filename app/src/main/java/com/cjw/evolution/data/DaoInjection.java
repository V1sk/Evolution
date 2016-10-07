package com.cjw.evolution.data;

import com.cjw.evolution.EvoApplication;
import com.cjw.evolution.data.source.local.db.DaoMaster;
import com.cjw.evolution.data.source.local.db.DaoSession;

import org.greenrobot.greendao.identityscope.IdentityScopeType;

/**
 * Created by CJW on 2016/10/2.
 */

public class DaoInjection {

    private  DaoMaster daoMaster;
    private  DaoSession daoSession;

    private DaoInjection() {

    }

    private static class SingletonHolder {
        private static final DaoInjection INSTANCE = new DaoInjection();
    }

    public static DaoInjection getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 取得DaoMaster
     *
     * @return
     */
    private DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(EvoApplication.getInstance(), "evolution_db", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @return
     */
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession(IdentityScopeType.None);
        }
        return daoSession;
    }

}
