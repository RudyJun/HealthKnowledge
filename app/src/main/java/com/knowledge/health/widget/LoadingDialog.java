package com.knowledge.health.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.knowledge.health.R;


/**
 * 加载框
 */
public class LoadingDialog extends Dialog {
    private TextView tvContent;
    public LoadingDialog(Context context) {
        this(context, R.style.LoadingDialog);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        this(context, R.style.LoadingDialog);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialog();
    }

    private void initDialog() {
        setContentView(R.layout.dialog_loading);
        getWindow().getAttributes().gravity = Gravity.CENTER;

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SpinKitView progressBar = (SpinKitView)findViewById(R.id.loading);
                FadingCircle fadingCircle = new FadingCircle();
                progressBar.setIndeterminateDrawable(fadingCircle);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        tvContent = (TextView) findViewById(R.id.tvContent);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                SpinKitView progressBar = (SpinKitView)findViewById(R.id.loading);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 设置提示语
     * @param content
     */
    public void setContent(String content) {
        tvContent.setText(content);
    }
}
