package com.knowledge.health.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.knowledge.health.R;
import com.knowledge.health.base.entity.TabEntity;
import com.knowledge.health.base.presenter.BasePresenter;
import com.knowledge.health.base.view.BaseActivity;
import com.knowledge.health.module.friend.view.FriendFragment;
import com.knowledge.health.module.discovery.view.DiscoveryFragment;
import com.knowledge.health.module.index.view.IndexFragment;
import com.knowledge.health.module.me.view.MeFragment;
import com.knowledge.health.widget.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by RudyJun on 2016/11/23.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.mViewPager)
    ScrollViewPager mViewPager;

    @BindView(R.id.tabLayout)
    CommonTabLayout tabLayout;

    // 首页所有页面
    private List<Fragment> fragments = new ArrayList<>(4);

    //底部tab标题
    private String[] titles = {"首页", "好友", "动态", "我的"};
    //底部选中图标
    private int[] selectedIconIds = {R.mipmap.icon_home_select, R.mipmap.icon_contacts_select,
            R.mipmap.icon_discover_select, R.mipmap.icon_my_select};
    //底部未选中图标
    private int[] unSelectedIconIds = {R.mipmap.icon_home, R.mipmap.icon_contacts,
            R.mipmap.icon_discover, R.mipmap.icon_my};
    private ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();

    private long mLastTime;

    @Override
    public int initResource() {
        return R.layout.activity_main;
    }

    protected void initData() {
        for (int i = 0; i < titles.length; i++) {
            tabEntities.add(new TabEntity(titles[i], selectedIconIds[i], unSelectedIconIds[i]));
        }
        fragments.add(new IndexFragment());
        fragments.add(new FriendFragment());
        fragments.add(new DiscoveryFragment());
        fragments.add(new MeFragment());
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    protected void initViews() {
        tabLayout.setTabData(tabEntities);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initEvents() {

    }


    private class PageAdapter extends FragmentPagerAdapter {
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }


    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastTime < 2000) {
            super.onBackPressed();
        } else {
            mLastTime = currentTime;
            showToast("再按一次退出应用");
        }
    }
}
