package com.knowledge.health.module.me.view.inter;

import com.knowledge.health.base.view.IView;
import com.knowledge.health.module.me.model.entity.LoginOrRegisterInfo;

/**
 * Created by RudyJun on 2017/1/7.
 */

public interface IRegisterView extends IView {
    void registerSuccess();

    void registerFail(String errorMsg);
}
