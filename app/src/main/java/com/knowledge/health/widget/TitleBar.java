package com.knowledge.health.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.knowledge.health.R;

/**
 * Created by RudyJun on 2017/1/7.
 */

public class TitleBar extends RelativeLayout {
    private ImageView ivBack;//返回按钮
    private TextView titleView;//顶部居中标题
    private TextView tvRight;//右边文本
    private LayoutInflater layoutInflater;

    private Context mContext;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void showWidget(View view) {
        view.setVisibility(VISIBLE);
    }

    public ImageView setBackFinish(final Activity activity) {
        showWidget(ivBack);
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return ivBack;
    }

    public ImageView getBackButton() {
        showWidget(ivBack);
        return ivBack;
    }

    public TextView getTitleView() {
        showWidget(titleView);
        return titleView;
    }

    public TextView setTitle(String title) {
        showWidget(titleView);
        titleView.setText(title);
        return titleView;
    }

    public TextView getRightView() {
        showWidget(tvRight);
        return tvRight;
    }

    public TextView setRightText(String text) {
        showWidget(tvRight);
        tvRight.setText(text);
        return tvRight;
    }

    private void init() {
        layoutInflater = LayoutInflater.from(mContext);
        layoutInflater.inflate(R.layout.title_bar, this);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        titleView = (TextView) findViewById(R.id.tvTitle);
        tvRight = (TextView) findViewById(R.id.tvRight);
    }

}
