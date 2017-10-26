package com.baway.a1508bnsg.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.baway.a1508bnsg.R;
import com.baway.a1508bnsg.adapter.CardAdapter;
import com.baway.a1508bnsg.bean.BaseBean;
import com.baway.a1508bnsg.bean.CardBean;
import com.baway.a1508bnsg.bean.MessageEvent;
import com.baway.a1508bnsg.bean.MsgMoneyCountEvent;
import com.baway.a1508bnsg.net.Api;
import com.baway.a1508bnsg.net.OnNetListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardActivity extends BaseActivity {

    private RecyclerView mRv;
    private CheckBox mCb;
    private TextView mTvContent;//用于设置钱和数量
    private List<CardBean.DatasBean.CartListBean.GoodsBean> list = new ArrayList<>();
    private CardAdapter adapter;
    private int count;
    private float sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        EventBus.getDefault().register(this);
        initView();
        getInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CardAdapter(this, list);
        mRv.setAdapter(adapter);
        mCb = (CheckBox) findViewById(R.id.cb);
        mTvContent = (TextView) findViewById(R.id.tvContent);
        //给全选的checkbox注册点击监听事件
        mCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.cbChecked(mCb.isChecked());
            }
        });

    }

    private void getInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("key", getSharedPreferences("nsg", Context.MODE_PRIVATE).getString("key", ""));
        httpUtil.doPost(Api.CARD, params, CardBean.class, new OnNetListener() {
            @Override
            public void onSuccess(BaseBean baseBean) throws IOException {
                CardBean bean = (CardBean) baseBean;
                adapter.refresh(bean.getDatas().getCart_list().get(0).getGoods());
            }

            @Override
            public void onError(IOException e) {

            }
        });
    }

    @Subscribe
    public void onMessageEvent(MessageEvent msg) {
        mCb.setChecked(msg.isChecked());
    }

    @Subscribe
    public void onMsgMCEvent(MsgMoneyCountEvent msg) {
        if (msg.isFlag()) {
            sum = 0;
            count = 0;
        }
        float money = msg.getMoney();
        int num = msg.getNum();
        sum += money;
        count += num;
        if (sum < 0 || count < 0) {
            sum = 0;
            count = 0;
        }
        mTvContent.setText("数量：" + count + "个 总计：" + sum + "元");
    }

}
