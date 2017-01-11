package com.knowledge.health.module.me.presenter;

import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.presenter.BasePresenter;
import com.knowledge.health.module.me.model.PersonalInfoService;
import com.knowledge.health.module.me.model.entity.StateInfo;
import com.knowledge.health.module.me.model.entity.UploadImage;
import com.knowledge.health.module.me.view.inter.IPersonalInfoView;
import com.knowledge.health.util.TaskManager;

import java.io.IOException;

/**
 * Created by RudyJun on 2017/1/10.
 */

public class PersonalInfoPresenter extends BasePresenter<IPersonalInfoView> {
    private PersonalInfoService service;

    public PersonalInfoPresenter(IPersonalInfoView view) {
        attachView(view);
        service = new PersonalInfoService();
    }

    public void modiyPersonInfo(final String access_token, final String signature, final int gender, final String qq, final String weibo) {
        TaskManager.BackgroundTask modiyPersonInfoBackground = new TaskManager.BackgroundTask() {
            @Override
            public Object doWork(Object data) throws NetworkDisconnectException, IOException {
                try {
                    return service.modifyPersonInfo(access_token, signature, gender, qq, weibo);
                } catch (NetworkDisconnectException | IOException e) {
                    hideLoading();
                    throw e;
                }
            }
        };

        TaskManager.UITask<StateInfo> uiTask = new TaskManager.UITask<StateInfo>() {
            @Override
            public Object doWork(StateInfo data) {
                if (view != null) {
                    if (data != null && data.isStatus()) {
                        view.modifySuccess();
                    } else if (data != null && !data.isStatus()) {
                        view.modifyFail(data.getMsg());
                    }
                }
                return null;
            }
        };

        new TaskManager()
                .next(modiyPersonInfoBackground)
                .next(uiTask)
                .start();
    }

    public void uploadImage(final String access_token, final String path) {
        TaskManager.BackgroundTask uploadImageBackground = new TaskManager.BackgroundTask() {
            @Override
            public Object doWork(Object data) throws NetworkDisconnectException, IOException {
                try {
                    return service.uploadImage(path);
                } catch (NetworkDisconnectException | IOException e) {
                    hideLoading();
                    throw e;
                }
            }
        };

        TaskManager.BackgroundTask<UploadImage> modifyBackground = new TaskManager.BackgroundTask<UploadImage>() {
            @Override
            public Object doWork(UploadImage data) throws IOException, NetworkDisconnectException {
                StateInfo stateInfo = null;
                try {
                    if (data != null && data.getState().equalsIgnoreCase("SUCCESS")) {
                        stateInfo = service.modifyAvatar(access_token, data.getUrl());
                        if (stateInfo != null && stateInfo.isStatus()) {
                            stateInfo.setMsg(data.getUrl());
                        }
                    } else if (data != null) {
                        stateInfo = new StateInfo();
                        stateInfo.setStatus(false);
                        stateInfo.setMsg(data.getState());
                        return stateInfo;
                    }
                } catch (NetworkDisconnectException | IOException e) {
                    hideLoading();
                    throw e;
                }
                return stateInfo;
            }
        };

        TaskManager.UITask<StateInfo> uiTask = new TaskManager.UITask<StateInfo>() {
            @Override
            public Object doWork(StateInfo data) {
                if (view != null) {
                    if (data != null && data.isStatus()) {
                        view.uploadSuccess(data.getMsg());
                    } else if (data != null) {
                        view.uploadFail(data.getMsg());
                    }
                }
                return null;
            }
        };

        new TaskManager()
                .next(uploadImageBackground)
                .next(modifyBackground)
                .next(uiTask)
                .start();
    }
}
