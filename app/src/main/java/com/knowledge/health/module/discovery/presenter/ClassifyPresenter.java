package com.knowledge.health.module.discovery.presenter;

import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.presenter.BasePresenter;
import com.knowledge.health.module.discovery.model.ClassifyService;
import com.knowledge.health.module.discovery.model.entity.ClassifyDetail;
import com.knowledge.health.module.discovery.view.inter.IClassifyView;
import com.knowledge.health.util.TaskManager;

import java.io.IOException;

/**
 * Created by RudyJun on 2017/1/12.
 */

public class ClassifyPresenter extends BasePresenter<IClassifyView> {
    private ClassifyService service;

    public ClassifyPresenter(IClassifyView view) {
        attachView(view);
        service = new ClassifyService();
    }

    public void classifyDetail(final String id, final int page) {
        TaskManager.BackgroundTask classifyDetailBackground = new TaskManager.BackgroundTask() {
            @Override
            public Object doWork(Object data) throws NetworkDisconnectException, IOException {
                try {
                    return service.classifyDetail(id , page);
                } catch (NetworkDisconnectException | IOException e) {
                    hideLoading();
                    throw e;
                }
            }
        };

        TaskManager.UITask<ClassifyDetail> uiTask = new TaskManager.UITask<ClassifyDetail>() {
            @Override
            public Object doWork(ClassifyDetail data) {
                if (view != null) {
                        view.classifyDetail(data);
                }
                return null;
            }
        };

        new TaskManager()
                .next(classifyDetailBackground)
                .next(uiTask)
                .start();
    }
}
