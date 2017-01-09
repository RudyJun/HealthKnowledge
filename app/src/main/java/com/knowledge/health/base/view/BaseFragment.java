package com.knowledge.health.base.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knowledge.health.HealthApplication;
import com.knowledge.health.base.presenter.BasePresenter;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;

/**
 * Created by RudyJun on 2016/12/22.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements IBaseView {
    protected String TAG = getClass().getSimpleName();

    protected T presenter;
    protected BaseActivity activity;
    // 是否延迟加载
    protected boolean isLazyLoad = false;
    protected boolean isDataInitiated;
    protected boolean isViewInitiated;
    protected View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
        presenter = createPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(initResource(), null);
        ButterKnife.bind(this, view);
        initViews();
        initEvents();
        return view;
    }

    protected abstract int initResource();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        prepareFetchData();
    }

    private void prepareFetchData() {
        if ((getUserVisibleHint() || !isLazyLoad) && isViewInitiated && !isDataInitiated) {
            fetchData();
            isDataInitiated = true;
        }

    }

    public void setLazyLoad(boolean lazyLoad) {
        isLazyLoad = lazyLoad;
    }

    protected abstract void initViews();

    protected abstract void fetchData();

    protected abstract void initEvents();


    protected abstract T createPresenter();

    @Override
    public void onDestroy() {
        if (null != presenter) {
            presenter.onDestory();
        }
        super.onDestroy();
        if (HealthApplication.getApplication().isDebug()) {
            RefWatcher refWatcher = HealthApplication.getRefWatcher(activity);
            refWatcher.watch(this);
        }
    }

    @Override
    public void showLoading(String tip) {
        if (activity != null) {
            activity.showLoading("");
        }

    }

    @Override
    public void showLoading(String tip, boolean cancelable, boolean touchCancelable) {
        if (activity != null) {
            activity.showLoading(tip, cancelable, touchCancelable);
        }
    }

    @Override
    public void hideLoading() {
        if (activity != null) {
            activity.hideLoading();
        }
    }

    @Override
    public void showToast(String message) {
        if (activity != null) {
            activity.showToast(message);
        }
    }
}
