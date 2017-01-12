package com.knowledge.health.module.me.view;

import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.knowledge.health.R;
import com.knowledge.health.base.view.BaseActivity;
import com.knowledge.health.module.me.model.entity.PersonInfo;
import com.knowledge.health.module.me.presenter.LoginPresenter;
import com.knowledge.health.module.me.view.inter.ILoginView;
import com.knowledge.health.util.LoginHelper;
import com.knowledge.health.util.StringUtil;
import com.knowledge.health.widget.TitleBar;

import butterknife.BindView;

/**
 * Created by RudyJun on 2017/1/7.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.ivShow)
    ImageView ivShow;

    private boolean showPassword;
    private String userName;
    private String passWord;

    @Override
    public int initResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        titleBar.setTitle("登录");
        titleBar.setRightText("注册")
                .setTextColor(Color.RED);
        titleBar.getRightView().setOnClickListener(this);
        titleBar.setBackFinish(this);
        initInputContent();
    }

    private void initInputContent() {
        if (!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(passWord)) {
            etUserName.setText(userName);
            etUserName.requestFocus();
            etUserName.setSelection(userName.length());
            etPassword.setText(passWord);
        }
    }

    @Override
    protected void initData() {
        userName = getIntent().getStringExtra("userName");
        passWord = getIntent().getStringExtra("password");
    }


    @Override
    protected void initEvents() {
        btLogin.setOnClickListener(this);
        ivShow.setOnClickListener(this);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btLogin:
                String account = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                if (StringUtil.isEmpty(account)) {
                    showToast("请输入用户名");
                    return;
                } else if (StringUtil.isEmpty(password) || password.length() < 8 || password.length() > 12) {
                    showToast("请输入8-12位密码");
                    return;
                }
                showLoading("登陆中");
                presenter.login(account, password);
                break;

            case R.id.ivShow:
                if (showPassword) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShow.setImageResource(R.mipmap.icon_unshow);
                    showPassword = false;
                } else {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivShow.setImageResource(R.mipmap.icon_show);
                    showPassword = true;
                }
                //将光标移至文本末尾
                int length = etPassword.getText().toString().length();
                etPassword.setSelection(length);
                etPassword.requestFocus();
                break;

            case R.id.tvRight:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void loginSuccess(PersonInfo data) {
        hideLoading();
        showToast("登录成功");
        LoginHelper.getInstance().saveLoginStatus(data);
        finish();
    }

    @Override
    public void loginFail(String errorMsg) {
        hideLoading();
        showToast(errorMsg);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        userName = intent.getStringExtra("userName");
        passWord = intent.getStringExtra("password");
        initInputContent();
    }
}
