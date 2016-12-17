package com.cjw.evolution.ui.profile;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjw.evolution.R;
import com.cjw.evolution.account.UserSession;
import com.cjw.evolution.data.model.User;
import com.cjw.evolution.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        initAppBar();
    }

    private void initAppBar() {
        User user = UserSession.getInstance().getUser();
        Glide.with(this)
                .load(user.getAvatar_url())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.head_default)
                .into(userAvatar);
    }

    @OnClick({R.id.btn_following, R.id.layout_following, R.id.layout_followers, R.id.layout_shots})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_following:
                break;
            case R.id.layout_following:
                break;
            case R.id.layout_followers:
                break;
            case R.id.layout_shots:
                break;
        }
    }
}
