package com.knowledge.health.module.discovery.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.knowledge.health.R;
import com.knowledge.health.base.adapter.CommonAdapter;
import com.knowledge.health.base.adapter.DividerItemDecoration;
import com.knowledge.health.base.view.BaseFragment;
import com.knowledge.health.module.discovery.model.entity.ClassifyDetail;
import com.knowledge.health.module.discovery.presenter.ClassifyPresenter;
import com.knowledge.health.module.discovery.view.inter.IClassifyView;
import com.knowledge.health.widget.ImageCacheView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by RudyJun on 2017/1/12.
 */

public class ClassifyFragment extends BaseFragment<ClassifyPresenter> implements IClassifyView, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private String classifyId;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private int page;

    private CommonAdapter<ClassifyDetail.TngouBean> commonAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //实现延时加载，当这个页面用户可见时再去加载数据
        setLazyLoad(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int initResource() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void initViews() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //设置下拉刷新圈圈颜色变化
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置RecyclerView的布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //给RecyclerView设置分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        //初始化适配器
        commonAdapter = new CommonAdapter<ClassifyDetail.TngouBean>(R.layout.rc_item_classfiy_detail, new ArrayList<ClassifyDetail.TngouBean>()) {
            @Override
            protected void convert(BaseViewHolder helper, ClassifyDetail.TngouBean item) {
                helper.setText(R.id.tvTitle, item.getTitle())
                        .setText(R.id.tvContent, item.getDescription())
                        .setText(R.id.tvTime, new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(item.getTime()));
                ImageCacheView ivImg = helper.getView(R.id.ivImg);
                ivImg.setImageSrc(item.getImg());
            }
        };

        mRecyclerView.setAdapter(commonAdapter);
        commonAdapter.setOnLoadMoreListener(this);
        //监听滚动状态，当滚动时停止图片加载，静止时再去加载图片
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING
                        || newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    Fresco.getImagePipeline().pause();
                } else {
                    Fresco.getImagePipeline().resume();
                }
            }
        });

        //设置Item和ItemChild的点击事件和长按事件
        mRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(activity, InfoDetailActivity.class);
                intent.putExtra("id", commonAdapter.getData().get(position).getId());
                intent.putExtra("title" , commonAdapter.getData().get(position).getTitle());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(activity, "长按了Item ：" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ivAvatar:
                        Toast.makeText(activity, "点击了头像", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.tvContent:
                        Toast.makeText(activity, "点击了内容", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ivAvatar:
                        Toast.makeText(activity, "长按了头像 ：" + position, Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.tvContent:
                        Toast.makeText(activity, "长按了内容 ：" + position, Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }

    @Override
    protected void fetchData() {
        page = 1;
        classifyId = getArguments().getString("id");
        showLoading("");
        presenter.classifyDetail(classifyId, page);

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected ClassifyPresenter createPresenter() {
        return new ClassifyPresenter(this);
    }

    @Override
    public void onRefresh() {
        //刷新时，不允许加载更多
        commonAdapter.setEnableLoadMore(false);
        page = 1;
        presenter.classifyDetail(classifyId, page);

    }

    @Override
    public void onLoadMoreRequested() {
        //加载更多时，不允许刷新
        mSwipeRefreshLayout.setEnabled(false);
        presenter.classifyDetail(classifyId, page);
    }

    @Override
    public void classifyDetail(ClassifyDetail data) {

        hideLoading();
        if (data == null || !data.isStatus()) {
            showToast("加载失败");
            return;
        }
        if (data.getTngou() != null && page == 1) {
            commonAdapter.setNewData(data.getTngou());
            mSwipeRefreshLayout.setRefreshing(false);
            commonAdapter.setEnableLoadMore(true);
        } else if (data.getTngou() != null) {
            commonAdapter.loadMoreComplete();
            commonAdapter.addData(data.getTngou());
            mSwipeRefreshLayout.setEnabled(true);
        }
        page++;


    }
}
