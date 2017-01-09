package com.knowledge.health.module.me.view;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.knowledge.health.R;
import com.knowledge.health.base.AppManager;
import com.knowledge.health.base.view.BaseActivity;
import com.knowledge.health.module.me.presenter.RegisterPresenter;
import com.knowledge.health.module.me.view.inter.IRegisterView;
import com.knowledge.health.util.StringUtil;
import com.knowledge.health.widget.TitleBar;

import butterknife.BindView;

/**
 * Created by RudyJun on 2017/1/9.
 */

public class BindingEmailActivity extends BaseActivity<RegisterPresenter> implements IRegisterView, View.OnClickListener {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.etEmail)
    EditText etEmail;
    private TextView textView;

    private String userName;
    private String passWord;

    @Override
    public int initResource() {
        return R.layout.activity_binding_email;
    }

    @Override
    protected void initViews() {
        titleBar.setBackFinish(this);
        titleBar.setTitle("绑定邮箱");
        textView = titleBar.setRightText("完成");
    }

    @Override
    protected void initData() {
        userName = getIntent().getStringExtra("userName");
        passWord = getIntent().getStringExtra("password");
    }

    @Override
    protected void initEvents() {
        textView.setOnClickListener(this);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvRight:
                String emailStr = etEmail.getText().toString();
                if (!StringUtil.isEmail(emailStr)) {
                    showToast("请输入正确的邮箱地址");
                    return;
                }
                presenter.register(userName, passWord, emailStr);
                showLoading("注册中");
                break;

            default:
                break;
        }
    }

    @Override
    public void registerSuccess() {
        hideLoading();
        showToast("注册成功");
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userName", userName);
        intent.putExtra("password", passWord);
        startActivity(intent);
        AppManager.getAppManager().finishActivity(RegisterActivity.class);
        finish();
    }

    @Override
    public void registerFail(String errorMsg) {
        hideLoading();
        if (errorMsg.contains("不允许重名")) {
            showToast("此用户已经注册");
        } else if (errorMsg.contains("邮箱地址已经注册")) {
            showToast("邮箱地址已经注册");
        } else {
            showToast(errorMsg);
        }
    }
}
