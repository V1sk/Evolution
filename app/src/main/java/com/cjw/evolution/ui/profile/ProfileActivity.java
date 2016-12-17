package com.cjw.evolution.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjw.evolution.R;
import com.cjw.evolution.account.UserSession;
import com.cjw.evolution.data.model.Following;
import com.cjw.evolution.data.model.User;
import com.cjw.evolution.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements ProfileContract.View {

    public static final String EXTRA_FOLLOWING = "following";

    @BindView(R.id.user_avatar)
    CircleImageView userAvatar;
    @BindView(R.id.btn_following)
    Button btnFollowing;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_bio)
    TextView userBio;
    @BindView(R.id.count_following)
    TextView countFollowing;
    @BindView(R.id.count_followers)
    TextView countFollowers;
    @BindView(R.id.count_shots)
    TextView countShots;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    private User user;
    private ProfileContract.Presenter presenter;
    private boolean following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        getExtras();
        initAppBar();
        setUserData();
        new ProfilePresenter(this).subscribe();
        presenter.following(user.getId());
    }

    private void getExtras() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_FOLLOWING)) {
            //from following
            Following following = intent.getParcelableExtra(EXTRA_FOLLOWING);
            user = following.getFollowee();
        } else {
            //load user's profile
            user = UserSession.getInstance().getUser();
        }
    }

    private void initAppBar() {
        Glide.with(this)
                .load(user.getAvatar_url())
                .centerCrop()
                .override(200, 200)
                .dontAnimate()
                .placeholder(R.drawable.head_default)
                .into(userAvatar);
    }

    private void setUserData() {
        userName.setText(user.getUsername());
        userBio.setText(user.getBio());
        countFollowing.setText(String.valueOf(user.getFollowings_count()));
        countFollowers.setText(String.valueOf(user.getFollowers_count()));
        countShots.setText(String.valueOf(user.getShots_count()));
    }

    @OnClick({R.id.btn_following, R.id.layout_following, R.id.layout_followers, R.id.layout_shots})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_following:
                if (following) {
                    presenter.unfollow(user.getId());
                } else {
                    presenter.follow(user.getId());
                }
                break;
            case R.id.layout_following:
                break;
            case R.id.layout_followers:
                break;
            case R.id.layout_shots:
                break;
        }
    }

    @Override
    public void onFollowingResult(boolean following) {
        this.following = following;
        if (following) {
            btnFollowing.setText(R.string.unfollow);
        } else {
            btnFollowing.setText(R.string.follow);
        }
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
