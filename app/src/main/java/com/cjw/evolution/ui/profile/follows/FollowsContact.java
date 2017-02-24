package com.cjw.evolution.ui.profile.follows;

import com.cjw.evolution.data.model.Follows;
import com.cjw.evolution.ui.base.BasePresenter;
import com.cjw.evolution.ui.base.BaseView;

import java.util.List;

/**
 * Created by chenjianwei on 2017/2/24.
 */

public class FollowsContact {

    interface View extends BaseView<Presenter> {

        void onListFollows(List<Follows> followsList);

        void loadFailed();

        void loadMoreFailed();

        void noMoreData();

    }

    interface Presenter extends BasePresenter {

        void listFollowing(long userId);

        void listFollowers(long userId);

    }

}
