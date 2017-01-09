package com.knowledge.health.module.me.view.inter;

import com.knowledge.health.base.view.IView;
import com.knowledge.health.module.me.model.entity.PersonInfo;

/**
 * Created by RudyJun on 2017/1/7.
 */

public interface ILoginView extends IView{
    void loginSuccess(PersonInfo data);

    void loginFail(String errorMsg);
}
