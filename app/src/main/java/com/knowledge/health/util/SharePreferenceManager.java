package com.knowledge.health.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.knowledge.health.HealthApplication;

/**
 * Created by RudyJun on 2017/1/9.
 * SharePreference的管理器
 */
public class SharePreferenceManager {
    public static final String LOGIN = "LOGIN";

/*    private static SharePreferenceManager instance;

    public static SharePreferenceManager getInstance() {
        if (instance == null) {
            synchronized (SharePreferenceManager.class) {
                instance = new SharePreferenceManager();
            }
        }
        return instance;
    }*/

    public SharePreferenceManager() {
    }

    /**
     * 获取指定名称的SharePreference
     *
     * @param name
     * @return
     */
    public static SharedPreferences getSharePreference(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getLoginSharePreference() {
        return getSharePreference(HealthApplication.getApplication(), LOGIN);
    }
}
