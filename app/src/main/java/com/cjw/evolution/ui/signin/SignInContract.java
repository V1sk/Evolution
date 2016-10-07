package com.cjw.evolution.ui.signin;

import com.cjw.evolution.ui.base.BasePresenter;
import com.cjw.evolution.ui.base.BaseView;


/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/13/16
 * Time: 9:54 PM
 * Desc: SignInContract
 */
public interface SignInContract {

    interface View extends BaseView<Presenter> {

        void onSignInStarted();

        void onSignInCompleted();

        void onSignInError(Throwable e);
    }

    interface Presenter extends BasePresenter {

        void signIn(String authorizeCode);
    }
}
