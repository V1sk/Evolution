package com.cjw.evolution.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjw.evolution.R;
import com.cjw.evolution.RxBus;
import com.cjw.evolution.account.UserSession;
import com.cjw.evolution.data.model.Following;
import com.cjw.evolution.data.model.Shots;
import com.cjw.evolution.data.model.User;
import com.cjw.evolution.event.FollowingEvent;
import com.cjw.evolution.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscription;
import rx.functions.Action1;

public class ProfileActivity extends BaseActivity implements ProfileContract.View {

    public static final String EXTRA_FOLLOWING = "following";
    private static final String TAG = "ProfileActivity";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private User user;
    private ProfileContract.Presenter presenter;
    private ProfileAdapter profileAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Shots> shotsList = new ArrayList<>();
    private HeaderViewHolder headerViewHolder;
    private boolean following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        getExtras();
        new ProfilePresenter(this).subscribe();
        setupRecyclerView();
        initEventBus();
    }

    private void initEventBus() {
        Subscription subscription = RxBus.getInstance().toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof FollowingEvent) {
                    if (following) {
                        presenter.unfollow(user.getId());
                    } else {
                        presenter.follow(user.getId());
                    }
                }
            }
        });
        addSubscription(subscription);
    }

    private void setupRecyclerView() {
        presenter.following(user.getId());
        profileAdapter = new ProfileAdapter(R.layout.item_profile_shots, shotsList);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_profile_header, null);
        profileAdapter.addHeaderView(view);
        headerViewHolder = new HeaderViewHolder(view);
        headerViewHolder.initData(user);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(profileAdapter);
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


    @Override
    public void onFollowingResult(boolean following) {
        this.following = following;
        headerViewHolder.following(following);
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    static class HeaderViewHolder {
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
        @BindView(R.id.layout_following)
        LinearLayout layoutFollowing;
        @BindView(R.id.count_followers)
        TextView countFollowers;
        @BindView(R.id.layout_followers)
        LinearLayout layoutFollowers;
        @BindView(R.id.count_shots)
        TextView countShots;
        @BindView(R.id.layout_shots)
        LinearLayout layoutShots;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void initData(User user) {
            Glide.with(userAvatar.getContext())
                    .load(user.getAvatar_url())
                    .centerCrop()
                    .override(200, 200)
                    .dontAnimate()
                    .placeholder(R.drawable.head_default)
                    .into(userAvatar);
            userName.setText(user.getUsername());
            userBio.setText(user.getBio());
            countFollowing.setText(String.valueOf(user.getFollowings_count()));
            countFollowers.setText(String.valueOf(user.getFollowers_count()));
            countShots.setText(String.valueOf(user.getShots_count()));
        }

        public void following(boolean following) {
            if (following) {
                btnFollowing.setText(R.string.unfollow);
            } else {
                btnFollowing.setText(R.string.follow);
            }
        }

        @OnClick({R.id.btn_following, R.id.layout_following, R.id.layout_followers, R.id.layout_shots})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_following:
                    RxBus.getInstance().post(new FollowingEvent());
                    break;
                case R.id.layout_following:
                    Log.d(TAG, "onClick: layout_following");
                    break;
                case R.id.layout_followers:
                    Log.d(TAG, "onClick: layout_followers");
                    break;
                case R.id.layout_shots:
                    Log.d(TAG, "onClick: layout_shots");
                    break;
            }
        }
    }
}
