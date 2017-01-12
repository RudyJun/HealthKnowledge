package com.knowledge.health.module.discovery.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.knowledge.health.R;
import com.knowledge.health.base.presenter.BasePresenter;
import com.knowledge.health.base.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by RudyJun on 2016/12/22.
 */

public class DiscoveryFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;

    private List<String> titleList;
    private List<Fragment> fragmentList;


    @Override
    public int initResource() {
        return R.layout.fragment_discovery;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //实现延时加载，当这个页面用户可见时再去加载数据
        setLazyLoad(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initViews() {
    }

    @Override
    public void fetchData() {
        titleList = new ArrayList<>();
        titleList.add("健康饮食");
        titleList.add("四季养生");
        titleList.add("医疗护理");
        titleList.add("心理健康");
        titleList.add("情感健康");


        fragmentList = new ArrayList<>();
        //健康饮食
        ClassifyFragment dietFragment = new ClassifyFragment();
        Bundle dietBundle = new Bundle();
        dietBundle.putString("id", "3");
        dietFragment.setArguments(dietBundle);
        fragmentList.add(dietFragment);

        //四季养生
        ClassifyFragment healthFragment = new ClassifyFragment();
        Bundle healthBundle = new Bundle();
        healthBundle.putString("id", "3");
        healthFragment.setArguments(healthBundle);
        fragmentList.add(healthFragment);

        //医疗护理
        ClassifyFragment careFragment = new ClassifyFragment();
        Bundle careBundle = new Bundle();
        careBundle.putString("id", "12");
        careFragment.setArguments(careBundle);
        fragmentList.add(careFragment);

        //心理健康
        ClassifyFragment psychicFragment = new ClassifyFragment();
        Bundle psychicBundle = new Bundle();
        psychicBundle.putString("id", "9");
        psychicFragment.setArguments(psychicBundle);
        fragmentList.add(psychicFragment);

        //情感健康
        ClassifyFragment emotionFragment = new ClassifyFragment();
        Bundle emotionBundle = new Bundle();
        emotionBundle.putString("id", "13");
        emotionFragment.setArguments(emotionBundle);
        fragmentList.add(emotionFragment);

        viewPager.setAdapter(new ClassifyPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public void initEvents() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public class ClassifyPagerAdapter extends FragmentPagerAdapter {

        public ClassifyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
