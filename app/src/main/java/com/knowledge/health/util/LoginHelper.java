package com.knowledge.health.util;

import android.content.SharedPreferences;

import com.knowledge.health.module.me.model.entity.PersonInfo;

/**
 * Created by RudyJun on 2017/1/9.
 * * 登录状态的帮助类
 */

public class LoginHelper {

    private static LoginHelper instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private PersonInfo loginPersonInfo;

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String ACCOUNT = "ACCOUNT";
    public static final String AVATAR = "AVATAR";
    public static final String DOMAIN = "DOMAIN";
    public static final String EMAIL = "EMAIL";
    public static final String GENDER = "GENDER";
    public static final String ID = "ID";
    public static final String INTEGRAL = "INTEGRAL";
    public static final String ISEMAIL = "ISEMAIL";
    public static final String ISPHONE = "ISPHONE";
    public static final String QQID = "QQID";
    public static final String TIME = "TIME";
    public static final String TITLE = "TITLE";
    public static final String WEIBOID = "WEIBOID";

    public static LoginHelper getInstance() {
        if (instance == null) {
            instance = new LoginHelper();
        }
        return instance;
    }

    private LoginHelper() {
        preferences = SharePreferenceManager.getLoginSharePreference();
        editor = preferences.edit();
        getLoginStatus();
    }

    //查询用户登录信息
    private void getLoginStatus() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setAccess_token(preferences.getString(ACCESS_TOKEN, ""));
        personInfo.setAccount(preferences.getString(ACCOUNT, ""));
        personInfo.setAvatar(preferences.getString(AVATAR, ""));
        personInfo.setEmail(preferences.getString(EMAIL, ""));
        personInfo.setTitle(preferences.getString(TITLE, ""));
        personInfo.setGender(preferences.getInt(GENDER, 0));
        personInfo.setIntegral(preferences.getInt(INTEGRAL, 0));
        if (!StringUtil.isEmpty(preferences.getString(ACCESS_TOKEN, ""))) {
            this.loginPersonInfo = personInfo;
        } else {
            loginPersonInfo = null;
        }
    }

    //保存用户登录信息
    public void saveLoginStatus(PersonInfo personInfo) {
        editor.putString(ACCESS_TOKEN, personInfo.getAccess_token());
        editor.putString(ACCOUNT, personInfo.getAccount());
        editor.putString(AVATAR, personInfo.getAvatar());
        editor.putString(EMAIL, personInfo.getEmail());
        editor.putString(TITLE, personInfo.getTitle());
        editor.putInt(GENDER, personInfo.getGender());
        editor.putInt(INTEGRAL, personInfo.getIntegral());
        editor.apply();
        this.loginPersonInfo = personInfo;
    }

    //退出登录，清空信息
    public void logout() {
        editor.clear();
        editor.apply();
        loginPersonInfo = null;
    }

    public boolean hasLogin() {
        return loginPersonInfo == null ? false : true;
    }

    public String getAccount() {
        return loginPersonInfo == null ? null : loginPersonInfo.getAccount();
    }
}
