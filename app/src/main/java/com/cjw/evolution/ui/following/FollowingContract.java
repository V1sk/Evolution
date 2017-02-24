package com.cjw.evolution.ui.following;

import com.cjw.evolution.data.model.Follows;
import com.cjw.evolution.ui.base.BasePresenter;
import com.cjw.evolution.ui.base.BaseView;

import java.util.List;

/**
 * Created by CJW on 2016/10/23.
 */

public interface FollowingContract {

    interface View extends BaseView<Presenter> {

        void showFollowing(List<Follows> followsList, boolean refresh);

        void onGetFollowingError(Throwable throwable);

        void showOrHideEmptyView();

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showLoadMoreFailed();

        void noMoreFollowing();
    }

    interface Presenter extends BasePresenter {

        void refresh();

        void loadMore();

    }

}
