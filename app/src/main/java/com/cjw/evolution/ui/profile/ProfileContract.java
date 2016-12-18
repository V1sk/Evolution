package com.cjw.evolution.ui.profile;

import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.ui.base.BasePresenter;
import com.cjw.evolution.ui.base.BaseView;

import java.util.List;

/**
 * Created by chenjianwei on 2016/12/17.
 */

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void onFollowingResult(boolean following);

        void onListUsersShots(List<Shots> shotsList);

    }

    interface Presenter extends BasePresenter {

        void following(long userId);

        void follow(long userId);

        void unfollow(long userId);

        void listShotsForUser(long userId, int page, int pageSize);

    }

}
