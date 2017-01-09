package com.knowledge.health.module.me.model;

import com.knowledge.health.base.config.GlobalConfig;
import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.model.ApiService;
import com.knowledge.health.module.me.model.entity.LoginOrRegisterInfo;
import com.knowledge.health.util.RetrofitClient;

import java.io.IOException;

/**
 * Created by RudyJun on 2017/1/7.
 */

public class RegisterService {
    public LoginOrRegisterInfo register(String userName, String password, String email) throws IOException, NetworkDisconnectException {
        ApiService apiService = RetrofitClient.getApiService();
        return RetrofitClient.getResult(apiService.register(GlobalConfig.CLIENT_ID, GlobalConfig.CLIENT_SECRET, userName, password, email));
    }
}
