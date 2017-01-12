package com.knowledge.health.module.me.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by RudyJun on 2017/1/10.
 * 只用作判断获取信息是否成功
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateInfo {

    private boolean status;
    private String msg;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
