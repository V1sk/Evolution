package com.cjw.evolution.ui.shotsdetail.likes;

import com.cjw.evolution.data.model.LikeUser;
import com.cjw.evolution.ui.base.BasePresenter;
import com.cjw.evolution.ui.base.BaseView;

import java.util.List;

/**
 * Created by chenjianwei on 2017/2/23.
 */

public interface LikesContract {

    interface View extends BaseView<Presenter> {

        void onListLikes(List<LikeUser> likeUserList);

        void loadFailed();

        void loadMoreFailed();

        void noMoreData();

    }

    interface Presenter extends BasePresenter {

        void listLikes(long shotsId);

    }

}
