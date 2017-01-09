package com.knowledge.health.module.index.view.inter;


import com.knowledge.health.base.view.IView;
import com.knowledge.health.module.index.model.entity.PhoneQuery;

/**
 * Created by RudyJun on 2016/12/8.
 */

public interface IExamplerView extends IView {

    void getPhoneQueryResult(PhoneQuery queryResult);
}
