package com.cjw.evolution.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjw.evolution.R;
import com.cjw.evolution.account.UserSession;
import com.cjw.evolution.data.model.User;
import com.cjw.evolution.ui.base.BaseActivity;
import com.cjw.evolution.ui.base.BaseFragment;
import com.cjw.evolution.ui.about.AboutFragment;
import com.cjw.evolution.ui.profile.ProfileActivity;
import com.cjw.evolution.ui.shots.ShotsFragment;
import com.cjw.evolution.ui.following.FollowingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initActionBar();
        initPagerData();
        setNavMenuClickListener();
        initNavigationView();
    }

    private void initPagerData() {
        final String[] tabTitles = getResources().getStringArray(R.array.evo_main_tab_titles);
        final BaseFragment[] fragments = new BaseFragment[tabTitles.length];
        fragments[0] = ShotsFragment.newInstance();
        fragments[1] = FollowingFragment.newInstance();
        fragments[2] = AboutFragment.newInstance();

        MainTabAdapter adapter = new MainTabAdapter(getSupportFragmentManager(), tabTitles, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.ff_padding_large));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initActionBar() {
        ActionBar ab = supportActionBar(toolbar);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        ab.setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        moveTaskToBack(true);
    }

    private void setNavMenuClickListener() {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.profile:
                        intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.buckets:
                        break;
                    case R.id.about:
                        break;
                    case R.id.sign_out:
                        break;
                }
                return true;
            }
        });
    }

    private void initNavigationView(){
        View navigationView =  navView.getHeaderView(0);
        final ImageView avatar = (ImageView) navigationView.findViewById(R.id.avatar);
        ImageView blurBg = (ImageView) navigationView.findViewById(R.id.blur_bg);
        TextView userName = (TextView) navigationView.findViewById(R.id.user_name);
        User user = UserSession.getInstance().getUser();
        Glide.with(this)
                .load(user.getAvatar_url())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.head_default)
                .into(avatar);
        Glide.with(this)
                .load(user.getAvatar_url())
                .bitmapTransform(new BlurTransformation(this, 20))
                .into(blurBg);
        userName.setText(user.getName());
    }
}
