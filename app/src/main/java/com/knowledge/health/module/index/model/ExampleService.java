package com.knowledge.health.module.index.model;

import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.model.ApiService;
import com.knowledge.health.module.index.model.entity.PhoneQuery;
import com.knowledge.health.util.RetrofitClient;

import java.io.IOException;

/**
 * Created by RudyJun on 2016/12/8.
 */

public class ExampleService {

    public PhoneQuery getPhoneQueryResult(String url, String phone) throws IOException, NetworkDisconnectException {
        String apikey = "563d6ca2b02ce6756724c8f0d89a4b8d";
        ApiService apiService = RetrofitClient.getService(url, ApiService.class);
        return RetrofitClient.getResult(apiService.phoneInvoices(apikey, phone));
    }

}
