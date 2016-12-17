package com.cjw.evolution.ui.profile;

import com.cjw.evolution.ui.base.BasePresenter;
import com.cjw.evolution.ui.base.BaseView;

/**
 * Created by chenjianwei on 2016/12/17.
 */

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void onFollowingResult(boolean following);

    }

    interface Presenter extends BasePresenter {

        void following(long userId);

        void follow(long userId);

        void unfollow(long userId);

    }

}
