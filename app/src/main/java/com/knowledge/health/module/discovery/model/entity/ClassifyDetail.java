package com.knowledge.health.module.discovery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.knowledge.health.base.Constants;

import java.util.List;

/**
 * Created by RudyJun on 2017/1/12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassifyDetail {

    private boolean status;
    private int total;
    private List<TngouBean> tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TngouBean> getTngou() {
        return tngou;
    }

    public void setTngou(List<TngouBean> tngou) {
        this.tngou = tngou;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TngouBean {
        private int count;
        private String description;
        private int fcount;
        private int id;
        private String img;
        private String keywords;
        private int loreclass;
        private int rcount;
        private long time;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFcount() {
            return fcount;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return Constants.AVATAR_URL + img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public int getLoreclass() {
            return loreclass;
        }

        public void setLoreclass(int loreclass) {
            this.loreclass = loreclass;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
