package com.knowledge.health.module.me.view.inter;

import com.knowledge.health.base.view.IView;

/**
 * Created by RudyJun on 2017/1/10.
 */

public interface IPersonalInfoView extends IView {
    void modifySuccess();

    void modifyFail(String msg);

    void uploadSuccess(String url);

    void uploadFail(String state);
}
