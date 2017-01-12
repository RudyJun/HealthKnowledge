package com.knowledge.health.module.discovery.view;

import android.webkit.WebView;
import android.widget.LinearLayout;

import com.knowledge.health.R;
import com.knowledge.health.base.view.BaseActivity;
import com.knowledge.health.module.discovery.model.entity.InfoDetail;
import com.knowledge.health.module.discovery.presenter.InfoDetailPresenter;
import com.knowledge.health.module.discovery.view.inter.IInfoDetailView;
import com.knowledge.health.widget.TitleBar;

import butterknife.BindView;

/**
 * Created by RudyJun on 2017/1/12.
 */

public class InfoDetailActivity extends BaseActivity<InfoDetailPresenter> implements IInfoDetailView {

    @BindView(R.id.titleBar)
    TitleBar titleBar;

    @BindView(R.id.llWebView)
    LinearLayout llWebView;

    private WebView webView;

    private int id;
    private String title;

    @Override
    protected int initResource() {
        return R.layout.activity_info_detail;
    }

    @Override
    protected void initViews() {
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        titleBar.setTitle(title);
        //WebView会造成内存泄漏，所以需要动态添加，然后在onDestroy方法中对WebView移除进行处理
        webView = new WebView(this);
        llWebView.addView(webView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        titleBar.setBackFinish(this);
        showLoading("");
        presenter.InfoDetail(id + "");
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected InfoDetailPresenter createPresenter() {
        return new InfoDetailPresenter(this);
    }

    @Override
    public void infoDetail(InfoDetail data) {
        hideLoading();
        if (data == null || !data.isStatus()) {
            showToast("获取信息失败");
            return;
        }

        String htmlPrefix = "<style type=\"text/css\">img {max-width: 100%;}</style>";
        webView.loadDataWithBaseURL(null, htmlPrefix + data.getMessage(), "text/html", "UTF-8", null);

    }

    @Override
    protected void onDestroy() {
        webView.stopLoading();
        llWebView.removeView(webView);
        webView.destroy();
        webView = null;
        super.onDestroy();
    }
}
