package com.knowledge.health.module.me.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by RudyJun on 2017/1/11.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
public class UploadImage {

    private String state;
    private String url;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
