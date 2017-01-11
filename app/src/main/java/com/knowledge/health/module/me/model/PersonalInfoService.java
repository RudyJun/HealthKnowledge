package com.knowledge.health.module.me.model;

import com.knowledge.health.base.exception.NetworkDisconnectException;
import com.knowledge.health.base.model.ApiService;
import com.knowledge.health.module.me.model.entity.StateInfo;
import com.knowledge.health.module.me.model.entity.UploadImage;
import com.knowledge.health.util.RetrofitClient;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by RudyJun on 2017/1/10.
 */

public class PersonalInfoService {
    public StateInfo modifyPersonInfo(String access_token, String signature, int gender, String qq, String weibo) throws IOException, NetworkDisconnectException {
        ApiService apiService = RetrofitClient.getApiService();
        return RetrofitClient.getResult(apiService.modiyPersonInfo(access_token, signature, gender, qq, weibo));
    }

    public UploadImage uploadImage(String path) throws IOException, NetworkDisconnectException {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("upfile", file.getName(), requestFile);
        ApiService apiService = RetrofitClient.getApiService();
        return RetrofitClient.getResult(apiService.uploadImage(part));
    }

    public StateInfo modifyAvatar(String access_token, String url) throws IOException, NetworkDisconnectException {
        ApiService apiService = RetrofitClient.getApiService();
        return RetrofitClient.getResult(apiService.modifyAvatar(access_token, url));
    }

}
