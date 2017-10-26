package com.baway.a1508bnsg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import com.baway.a1508bnsg.R;
import com.baway.a1508bnsg.adapter.GoodsListAdapter;
import com.baway.a1508bnsg.bean.BaseBean;
import com.baway.a1508bnsg.bean.GoodListBean;
import com.baway.a1508bnsg.net.Api;
import com.baway.a1508bnsg.net.OnNetListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表
 */
public class GoodsListActivity extends BaseActivity {

    private RecyclerView mRv;
    private SwipeRefreshLayout mSrl;
    private List<GoodListBean.DatasBean.GoodsListBean> list = new ArrayList<>();
    private GoodsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        initView();
        //获取gc_id
        Intent intent = getIntent();
        String gcId = intent.getStringExtra("gcId");
        getInfo(true);


    }

    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GoodsListAdapter(GoodsListActivity.this, list);
        mRv.setAdapter(adapter);
        adapter.setOnItemListenr(new GoodsListAdapter.OnItemListener() {
            @Override
            public void onItemClick(GoodListBean.DatasBean.GoodsListBean goodsListBean) {
                //跳转到详情界面
                Intent intent = new Intent(GoodsListActivity.this,GoodsDetailActivity.class);
                intent.putExtra("goods_id",goodsListBean.getGoods_id());
                startActivity(intent);

            }
        });
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInfo(true);
            }
        });
        //加载更多
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRv.getLayoutManager();
                int childCount = layoutManager.getItemCount();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                /**
                 * 判断是否滑动到了底部
                 */
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (lastVisibleItemPosition == childCount - 1) {
                        getInfo(false);
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void getInfo(final boolean isRefresh) {
        httpUtil.doGet(Api.GOODS_LIST, GoodListBean.class, new OnNetListener() {
            @Override
            public void onSuccess(BaseBean baseBean) throws IOException {
                GoodListBean goodListBean = (GoodListBean) baseBean;
                if (isRefresh) {
                    adapter.refreshData(goodListBean.getDatas().getGoods_list());
                    mSrl.setRefreshing(false);
                } else {
                    adapter.loadMore(goodListBean.getDatas().getGoods_list());
                }
            }

            @Override
            public void onError(IOException e) {

            }
        });
    }
}
