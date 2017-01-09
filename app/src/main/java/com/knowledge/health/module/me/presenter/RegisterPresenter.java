package com.knowledge.health.module.me.presenter;

import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.presenter.BasePresenter;
import com.knowledge.health.module.me.model.RegisterService;
import com.knowledge.health.module.me.model.entity.LoginOrRegisterInfo;
import com.knowledge.health.module.me.view.inter.IRegisterView;
import com.knowledge.health.util.TaskManager;

import java.io.IOException;

/**
 * Created by RudyJun on 2017/1/7.
 */

public class RegisterPresenter extends BasePresenter<IRegisterView> {
    private RegisterService service;

    public RegisterPresenter(IRegisterView view) {
        attachView(view);
        service = new RegisterService();
    }

    public void register(final String userName, final String password, final String email) {
        TaskManager.BackgroundTask registerBackground = new TaskManager.BackgroundTask() {
            @Override
            public Object doWork(Object data) throws NetworkDisconnectException, IOException {
                try {
                    return service.register(userName, password, email);
                } catch (NetworkDisconnectException | IOException e) {
                    hideLoading();
                    throw e;
                }
            }
        };

        TaskManager.UITask<LoginOrRegisterInfo> uiTask = new TaskManager.UITask<LoginOrRegisterInfo>() {
            @Override
            public Object doWork(LoginOrRegisterInfo data) {
                if (view != null) {
                    if (data != null && data.isStatus()) {
                        view.registerSuccess();
                    } else if(data != null && !data.isStatus()){
                        view.registerFail(data.getMsg());
                    }
                }
                return null;
            }
        };

        new TaskManager()
                .next(registerBackground)
                .next(uiTask)
                .start();
    }
}
