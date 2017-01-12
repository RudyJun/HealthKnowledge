package com.knowledge.health.module.me.presenter;

import android.util.Log;

import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.presenter.BasePresenter;
import com.knowledge.health.module.me.model.LoginService;
import com.knowledge.health.module.me.model.entity.LoginOrRegisterInfo;
import com.knowledge.health.module.me.model.entity.PersonInfo;
import com.knowledge.health.module.me.view.inter.ILoginView;
import com.knowledge.health.util.TaskManager;

import java.io.IOException;

/**
 * Created by RudyJun on 2017/1/7.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {
    private LoginService service;

    public LoginPresenter(ILoginView view) {
        attachView(view);
        service = new LoginService();
    }

    public void login(final String account, final String password) {
        TaskManager.BackgroundTask loginBackground = new TaskManager.BackgroundTask() {
            @Override
            public Object doWork(Object data) throws NetworkDisconnectException, IOException {
                try {
                    return service.login(account, password);
                } catch (NetworkDisconnectException | IOException e) {
                    Log.e("IOException1" , e.toString());
                    hideLoading();
                    throw e;
                }
            }
        };

        TaskManager.BackgroundTask<LoginOrRegisterInfo> personInfoBackground = new TaskManager.BackgroundTask<LoginOrRegisterInfo>() {
            @Override
            public Object doWork(LoginOrRegisterInfo data) throws IOException, NetworkDisconnectException {
                if (data != null) {
                    try {
                        //登录成功后查询个人信息
                        if (data.isStatus()) {
                            return service.queryPersonInfo(data.getAccess_token());
                        } else {
                            //返回失败登录原因
                            PersonInfo personInfo = new PersonInfo();
                            personInfo.setStatus(false);
                            personInfo.setMsg(data.getMsg());
                            return personInfo;
                        }
                    } catch (NetworkDisconnectException | IOException e) {
                        Log.e("IOException2" , e.toString());
                        hideLoading();
                        throw e;
                    }
                }
                return null;
            }
        };

        TaskManager.UITask<PersonInfo> uiTask = new TaskManager.UITask<PersonInfo>() {
            @Override
            public Object doWork(PersonInfo data) {
                if (view != null) {
                    if (data != null && data.isStatus()) {
                        view.loginSuccess(data);
                    } else if (data != null && !data.isStatus()) {
                        view.loginFail(data.getMsg());
                    }
                }
                return null;
            }
        };


        new TaskManager()
                .next(loginBackground)
                .next(personInfoBackground)
                .next(uiTask)
                .start();
    }
}
