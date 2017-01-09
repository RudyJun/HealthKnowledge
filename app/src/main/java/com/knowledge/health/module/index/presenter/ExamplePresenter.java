package com.knowledge.health.module.index.presenter;

import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.presenter.BasePresenter;
import com.knowledge.health.module.index.model.ExampleService;
import com.knowledge.health.module.index.model.entity.PhoneQuery;
import com.knowledge.health.module.index.view.inter.IExamplerView;
import com.knowledge.health.util.TaskManager;

import java.io.IOException;

/**
 * Created by RudyJun on 2016/12/8.
 */

public class ExamplePresenter extends BasePresenter<IExamplerView> {

    private ExampleService service;

    public ExamplePresenter(IExamplerView view) {
        attachView(view);
        service = new ExampleService();
    }

    public void getPhoneQuery(final String url, final String phone) {
        TaskManager.BackgroundTask phoneQueryBackgroundTask = new TaskManager.BackgroundTask() {
            @Override
            public Object doWork(Object data) throws NetworkDisconnectException, IOException {
                try {
                    return service.getPhoneQueryResult(url, phone);
                } catch (NetworkDisconnectException | IOException e) {
                    hideLoading();
                    throw e;
                }
            }
        };

        TaskManager.UITask<PhoneQuery> uiTask = new TaskManager.UITask<PhoneQuery>() {
            @Override
            public Object doWork(PhoneQuery data) {
                if (view != null) {
                    view.getPhoneQueryResult(data);
                }
                return null;
            }
        };

        new TaskManager()
                .next(phoneQueryBackgroundTask)
                .next(uiTask)
                .start();
    }
}
