package com.knowledge.health.module.me.view;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.knowledge.health.HealthApplication;
import com.knowledge.health.R;
import com.knowledge.health.base.Constants;
import com.knowledge.health.base.view.BaseActivity;
import com.knowledge.health.module.me.model.entity.PersonInfo;
import com.knowledge.health.module.me.presenter.PersonalInfoPresenter;
import com.knowledge.health.module.me.view.inter.IPersonalInfoView;
import com.knowledge.health.util.LoginHelper;
import com.knowledge.health.util.StringUtil;
import com.knowledge.health.widget.CircleImageView;
import com.knowledge.health.widget.TitleBar;
import com.knowledge.health.widget.selectphoto.TakePhoto;
import com.knowledge.health.widget.selectphoto.TakePhotoCallback;

import butterknife.BindView;

/**
 * Created by RudyJun on 2017/1/10.
 */

public class PersonalInfoActivity extends BaseActivity<PersonalInfoPresenter> implements IPersonalInfoView {

    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.ivAvatar)
    CircleImageView ivAvatar;
    @BindView(R.id.etSignature)
    EditText etSignature;
    @BindView(R.id.etQQ)
    EditText etQQ;
    @BindView(R.id.etWeibo)
    EditText etWeibo;
    @BindView(R.id.rgSexChoose)
    RadioGroup rgSexChoose;
    @BindView(R.id.rbMan)
    RadioButton rbMan;
    @BindView(R.id.rbWoman)
    RadioButton rbWoman;
    @BindView(R.id.rbSecret)
    RadioButton rbSecret;
    private TextView tvModify;

    private int gender;
    private String originalQQ;
    private String orginalWeibo;
    private String orginalSignature;
    private int orginalGender;

    private TakePhoto takePhoto;
    private PersonInfo personInfo;
    //标志已修改过头像
    private boolean hasChange;

    @Override
    protected int initResource() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void initViews() {
        titleBar.setBackFinish(this);
        titleBar.setTitle("个人信息");
        tvModify = titleBar.setRightText("修改");
        gender = LoginHelper.getInstance().getGender();
        if (StringUtil.isEmpty(LoginHelper.getInstance().getAvatar())) {
            ivAvatar.setImageResource(R.drawable.default_profile);
        } else {
            ivAvatar.setImageSrc(Constants.AVATAR_URL + LoginHelper.getInstance().getAvatar());
        }

        switch (gender) {
            case 0:
                rbWoman.setChecked(true);
                break;

            case 1:
                rbMan.setChecked(true);
                break;

            case -1:
                rbSecret.setChecked(true);
                break;
        }
        if (!StringUtil.isEmpty(orginalSignature)) {
            etSignature.setText(orginalSignature);
            etSignature.setSelection(orginalSignature.length());

        }
        if (!StringUtil.isEmpty(originalQQ)) {
            etQQ.setText(originalQQ);
        }
        if (!StringUtil.isEmpty(orginalWeibo)) {
            etWeibo.setText(orginalWeibo);
        }

        takePhoto = new TakePhoto.Builder().init(this).uri(HealthApplication.getApplication()
                .getAppCacheDir()).callback(new TakePhotoCallback() {
            @Override
            public void callback(Uri uri) {
                showLoading("上传头像中");
                presenter.uploadImage(LoginHelper.getInstance().getAccessToken(), uri.getPath());
            }
        }).build();
    }

    @Override
    protected void initData() {
        originalQQ = LoginHelper.getInstance().getQq();
        orginalWeibo = LoginHelper.getInstance().getWeibo();
        orginalSignature = LoginHelper.getInstance().getSignature();
        orginalGender = LoginHelper.getInstance().getGender();
        personInfo = new PersonInfo();
    }

    @Override
    protected void initEvents() {
        tvModify.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        rgSexChoose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbMan:
                        gender = 1;
                        break;

                    case R.id.rbWoman:
                        gender = 0;
                        break;

                    case R.id.rbSecret:
                        gender = -1;
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAvatar:
                takePhoto.initTakePhoto();
                break;

            case R.id.tvRight:
                String signature = etSignature.getText().toString() + "";
                String qq = etQQ.getText().toString() + "";
                String weibo = etWeibo.getText().toString() + "";
                String access_token = LoginHelper.getInstance().getAccessToken();
                //至少修改了一项信息才能提交，否则提示用户进行修改
                if (!signature.equals(orginalSignature) || !qq.equals(originalQQ) || gender != orginalGender || !weibo.equals(orginalWeibo)) {
                    personInfo.setSignature(signature);
                    personInfo.setQq(qq);
                    personInfo.setWeibo(weibo);
                    personInfo.setGender(gender);
                    presenter.modiyPersonInfo(access_token, signature, gender, qq, weibo);
                    showLoading("修改中");
                } else if (hasChange) {
                    finish();
                } else {
                    showToast("请修改信息");
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected PersonalInfoPresenter createPresenter() {
        return new PersonalInfoPresenter(this);
    }

    @Override
    public void modifySuccess() {
        hideLoading();
        LoginHelper.getInstance().saveModifyInfo(personInfo);
        showToast("修改信息成功");
        finish();
    }

    @Override
    public void modifyFail(String msg) {
        hideLoading();
        showToast("修改信息失败," + msg);
    }

    @Override
    public void uploadSuccess(String url) {
        hideLoading();
        LoginHelper.getInstance().saveAvatar(url);
        hasChange = true;
        if (ivAvatar != null) {
            ivAvatar.setImageSrc(url);
            showToast("头像修改成功");
        }

    }

    @Override
    public void uploadFail(String state) {
        hideLoading();
        showToast(state);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePhoto.onActivityResult(requestCode, resultCode, data);
    }

}
