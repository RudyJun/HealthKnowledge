package com.knowledge.health.module.discovery.presenter;

import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.presenter.BasePresenter;
import com.knowledge.health.module.discovery.model.InfoDetailService;
import com.knowledge.health.module.discovery.model.entity.InfoDetail;
import com.knowledge.health.module.discovery.view.inter.IInfoDetailView;
import com.knowledge.health.util.TaskManager;

import java.io.IOException;

/**
 * Created by RudyJun on 2017/1/12.
 */

public class InfoDetailPresenter extends BasePresenter<IInfoDetailView> {
    private InfoDetailService service;

    public InfoDetailPresenter(IInfoDetailView view) {
        attachView(view);
        service = new InfoDetailService();
    }

    public void InfoDetail(final String id) {
        TaskManager.BackgroundTask InfoDetailBackground = new TaskManager.BackgroundTask() {
            @Override
            public Object doWork(Object data) throws NetworkDisconnectException, IOException {
                try {
                    return service.infoDetail(id);
                } catch (NetworkDisconnectException | IOException e) {
                    hideLoading();
                    throw e;
                }
            }
        };

        TaskManager.UITask<InfoDetail> uiTask = new TaskManager.UITask<InfoDetail>() {
            @Override
            public Object doWork(InfoDetail data) {
                if (view != null) {
                    view.infoDetail(data);
                }
                return null;
            }
        };

        new TaskManager()
                .next(InfoDetailBackground)
                .next(uiTask)
                .start();
    }
}
