package com.knowledge.health.module.discovery.model;

import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.model.ApiService;
import com.knowledge.health.module.discovery.model.entity.ClassifyDetail;
import com.knowledge.health.util.RetrofitClient;

import java.io.IOException;

/**
 * Created by RudyJun on 2017/1/12.
 */

public class ClassifyService {

    public ClassifyDetail classifyDetail(String id , int page) throws IOException, NetworkDisconnectException {
        ApiService apiService = RetrofitClient.getApiService();
        return RetrofitClient.getResult(apiService.classifyDetail(id , page));
    }
}
