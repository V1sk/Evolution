package com.cjw.evolution.ui.shots;

import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.ui.base.BasePresenter;
import com.cjw.evolution.ui.base.BaseView;

import java.util.List;

/**
 * Created by CJW on 2016/10/2.
 */

public interface ShotsContract {

    interface View extends BaseView<Presenter> {

        void showShots(List<Shots> shotsList,boolean refresh);

        void onGetShotsError(Throwable throwable);

        void showOrHideEmptyView();

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void onLoadMoreStatusChange(int status);

    }

    interface Presenter extends BasePresenter{

        void refresh(String sort);

        void loadMore(String sort);

    }

}
