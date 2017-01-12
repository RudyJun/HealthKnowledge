package com.knowledge.health.module.me.view;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.knowledge.health.R;
import com.knowledge.health.base.view.BaseFragment;
import com.knowledge.health.module.me.presenter.MePresenter;
import com.knowledge.health.module.me.view.inter.IMeView;
import com.knowledge.health.util.LoginHelper;
import com.knowledge.health.util.StringUtil;
import com.knowledge.health.widget.CircleImageView;
import com.knowledge.health.widget.TitleBar;

import butterknife.BindView;

/**
 * Created by RudyJun on 2016/12/22.
 */

public class MeFragment extends BaseFragment<MePresenter> implements View.OnClickListener, IMeView {


    @BindView(R.id.titleBar)
    TitleBar titleBar;

    @BindView(R.id.ivAvatar)
    CircleImageView ivAvatar;

    @BindView(R.id.tvLogin)
    TextView tvLogin;

    @BindView(R.id.llWithoutLogin)
    View llWithoutLogin;

    @BindView(R.id.tvRegister)
    TextView tvRegister;

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.tvSignature)
    TextView tvSignature;

    @BindView(R.id.tvLogOut)
    TextView tvLogOut;

    @Override
    protected int initResource() {
        return R.layout.fragment_me;
    }

    @Override
    public void onResume() {
        super.onResume();
        initUserInfo();
    }

    private void initUserInfo() {
        if (LoginHelper.getInstance().hasLogin()) {
            llWithoutLogin.setVisibility(View.GONE);
            tvUserName.setVisibility(View.VISIBLE);
            tvUserName.setText(LoginHelper.getInstance().getAccount());
            tvLogOut.setVisibility(View.VISIBLE);
            tvSignature.setVisibility(View.VISIBLE);
            if (StringUtil.isEmpty(LoginHelper.getInstance().getSignature())) {
                tvSignature.setText("这人很懒，什么都没留下");
            } else {
                tvSignature.setText(LoginHelper.getInstance().getSignature());
            }
            ivAvatar.setImageSrc(LoginHelper.getInstance().getAvatar());
        } else {
            llWithoutLogin.setVisibility(View.VISIBLE);
            tvUserName.setVisibility(View.GONE);
            tvLogOut.setVisibility(View.GONE);
            tvSignature.setVisibility(View.GONE);
            ivAvatar.setImageResource(R.drawable.default_profile);
        }
    }

    @Override
    protected void initViews() {
        titleBar.setTitle("我的");
    }

    @Override
    protected void initEvents() {
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvLogOut.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
    }

    @Override
    protected void fetchData() {
    }

    @Override
    protected MePresenter createPresenter() {
        return new MePresenter(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvLogin:
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.tvRegister:
                intent = new Intent(activity, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.tvLogOut:
                LoginHelper.getInstance().logout();
                initUserInfo();
                showToast("退出登录");
                break;

            case R.id.ivAvatar:
                if (!LoginHelper.getInstance().hasLogin()) {
                    LoginHelper.getInstance().gotoLogin(activity);
                    return;
                }
                intent = new Intent(activity, PersonalInfoActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }
}
