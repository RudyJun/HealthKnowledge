package com.knowledge.health.module.me.view;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.knowledge.health.R;
import com.knowledge.health.base.presenter.BasePresenter;
import com.knowledge.health.base.view.BaseActivity;
import com.knowledge.health.util.StringUtil;
import com.knowledge.health.widget.TitleBar;

import butterknife.BindView;

/**
 * Created by RudyJun on 2017/1/7.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btNextStep)
    Button btNextStep;
    @BindView(R.id.ivShow)
    ImageView ivShow;

    private boolean showPassword;

    @Override
    public int initResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
        titleBar.setTitle("注册");
        titleBar.setBackFinish(this);
    }

    @Override
    protected void initEvents() {
        btNextStep.setOnClickListener(this);
        ivShow.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNextStep:
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                if (StringUtil.isEmpty(userName)) {
                    showToast("请输入用户名");
                    return;
                } else if (StringUtil.isEmpty(password) || password.length() < 8 || password.length() > 12) {
                    showToast("请输入8-12位密码");
                    return;
                }
                Intent intent = new Intent(this, BindingEmailActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("password", password);
                startActivity(intent);
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
            default:
                break;
        }
    }
}
