package com.knowledge.health.base.model;


import com.knowledge.health.module.index.model.entity.PhoneQuery;
import com.knowledge.health.module.me.model.entity.LoginOrRegisterInfo;
import com.knowledge.health.module.me.model.entity.PersonInfo;
import com.knowledge.health.module.me.model.entity.StateInfo;
import com.knowledge.health.module.me.model.entity.UploadImage;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by RudyJun on 2016/12/28.
 */

public interface ApiService {

    @GET("api/mobilenumber")
    Call<PhoneQuery> phoneInvoices(@Header("apikey") String apikey, @Query("phone") String phone);

    @FormUrlEncoded
    @POST("api/oauth2/reg")
    Call<LoginOrRegisterInfo> register(@Field("client_id") String clientId, @Field("client_secret") String clientSecret,
                                       @Field("account") String userName, @Field("password") String password,
                                       @Field("email") String email);

    @FormUrlEncoded
    @POST("api/oauth2/login")
    Call<LoginOrRegisterInfo> login(@Field("client_id") String clientId, @Field("client_secret") String clientSecret,
                                    @Field("name") String account, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/user")
    Call<PersonInfo> queryPersonInfo(@Field("access_token") String accessToken);

    @FormUrlEncoded
    @POST("api/user/profile")
    Call<StateInfo> modiyPersonInfo(@Field("access_token") String access_token, @Field("signature") String signature,

                                    @Field("gender") int gender, @Field("qq") String qq, @Field("weibo") String weibo);

    //单张头像图片上传
    @Multipart
    @POST("tnfs/action/controller?action=uploadimage&path=avatar")
    Call<UploadImage> uploadImage(@Part() MultipartBody.Part file);

    @FormUrlEncoded
    @POST("api/user/portrait")
    Call<StateInfo> modifyAvatar(@Field("access_token") String accessToken, @Field("url") String url);

}
