package com.knowledge.health.module.discovery.view.inter;

import com.knowledge.health.base.view.IView;
import com.knowledge.health.module.discovery.model.entity.ClassifyDetail;

/**
 * Created by RudyJun on 2017/1/12.
 */

public interface IClassifyView extends IView{
    void classifyDetail(ClassifyDetail data);
}
