package com.knowledge.health.base.model;


import com.knowledge.health.module.index.model.entity.PhoneQuery;
import com.knowledge.health.module.me.model.entity.LoginOrRegisterInfo;
import com.knowledge.health.module.me.model.entity.PersonInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by RudyJun on 2016/12/28.
 */

public interface ApiService {

    @GET("mobilenumber")
    Call<PhoneQuery> phoneInvoices(@Header("apikey") String apikey, @Query("phone") String phone);

    @FormUrlEncoded
    @POST("oauth2/reg")
    Call<LoginOrRegisterInfo> register(@Field("client_id") String clientId, @Field("client_secret") String clientSecret,
                                       @Field("account") String userName, @Field("password") String password,
                                       @Field("email") String email);

    @FormUrlEncoded
    @POST("oauth2/login")
    Call<LoginOrRegisterInfo> login(@Field("client_id") String clientId, @Field("client_secret") String clientSecret,
                                    @Field("name") String account, @Field("password") String password);

    @FormUrlEncoded
    @POST("user")
    Call<PersonInfo> queryPersonInfo(@Field("access_token") String accessToken);
}
