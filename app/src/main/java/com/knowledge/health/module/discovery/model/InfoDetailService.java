package com.knowledge.health.module.discovery.model;

import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.model.ApiService;
import com.knowledge.health.module.discovery.model.entity.InfoDetail;
import com.knowledge.health.util.RetrofitClient;

import java.io.IOException;

/**
 * Created by RudyJun on 2017/1/12.
 */

public class InfoDetailService {
    public InfoDetail infoDetail(String id) throws IOException, NetworkDisconnectException {
        ApiService apiService = RetrofitClient.getApiService();
        return RetrofitClient.getResult(apiService.infoDetail(id));
    }
}
