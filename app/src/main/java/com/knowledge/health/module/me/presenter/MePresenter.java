package com.knowledge.health.module.me.presenter;

import com.knowledge.health.base.presenter.BasePresenter;
import com.knowledge.health.module.me.model.MeService;
import com.knowledge.health.module.me.view.inter.IMeView;

/**
 * Created by RudyJun on 2017/1/7.
 */

public class MePresenter extends BasePresenter<IMeView> {

    private MeService service;

    public MePresenter(IMeView view) {
        attachView(view);
        service = new MeService();
    }
}
