package com.knowledge.health.module.me.model;

import com.knowledge.health.base.config.GlobalConfig;
import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.model.ApiService;
import com.knowledge.health.module.me.model.entity.LoginOrRegisterInfo;
import com.knowledge.health.module.me.model.entity.PersonInfo;
import com.knowledge.health.util.RetrofitClient;

import java.io.IOException;

/**
 * Created by RudyJun on 2017/1/7.
 */

public class LoginService {
    public LoginOrRegisterInfo login(String account, String password) throws IOException, NetworkDisconnectException {
        ApiService apiService = RetrofitClient.getApiService();
        return RetrofitClient.getResult(apiService.login(GlobalConfig.CLIENT_ID, GlobalConfig.CLIENT_SECRET, account, password));
    }

    public PersonInfo queryPersonInfo(String accessToken) throws IOException, NetworkDisconnectException {
        ApiService apiService = RetrofitClient.getApiService();
        return RetrofitClient.getResult(apiService.queryPersonInfo(accessToken));
    }
}
