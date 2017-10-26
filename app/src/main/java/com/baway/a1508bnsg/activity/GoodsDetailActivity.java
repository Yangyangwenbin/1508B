package com.baway.a1508bnsg.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baway.a1508bnsg.R;
import com.baway.a1508bnsg.bean.AddCardBean;
import com.baway.a1508bnsg.bean.BaseBean;
import com.baway.a1508bnsg.bean.GoodsDetailBean;
import com.baway.a1508bnsg.net.Api;
import com.baway.a1508bnsg.net.OnNetListener;
import com.baway.a1508bnsg.widget.AddDelView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener {

    private String goods_id;
    private SimpleDraweeView mSdv;
    private TextView mTvGoodsMsg;
    private LinearLayout mLlAddCard;
    private RelativeLayout rl;
    private GoodsDetailBean goodsDetailBean;
    private PopupWindow popupWindow;
    private LinearLayout mLlToCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        initView();
        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
        getInfo();

    }

    private void getInfo() {
        String url = String.format(Api.GOODS_DETAILS, goods_id);
        httpUtil.doGet(url, GoodsDetailBean.class, new OnNetListener() {
            @Override
            public void onSuccess(BaseBean baseBean) throws IOException {
                goodsDetailBean = (GoodsDetailBean) baseBean;
                mSdv.setImageURI(goodsDetailBean.getDatas().getSpec_image().get(0));
                mTvGoodsMsg.setText(goodsDetailBean.getDatas().getGoods_info().getGoods_name());
            }
            @Override
            public void onError(IOException e) {

            }
        });
    }
    private void initView() {
        mSdv = (SimpleDraweeView) findViewById(R.id.sdv);
        rl = (RelativeLayout) findViewById(R.id.rl);
        mTvGoodsMsg = (TextView) findViewById(R.id.tv_goodsMsg);
        mLlAddCard = (LinearLayout) findViewById(R.id.ll_addCard);
        mLlAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出一个popupWindow,确认商品
                showPop();
            }
        });
        mLlToCard = (LinearLayout) findViewById(R.id.ll_toCard);
        mLlToCard.setOnClickListener(this);
    }

    private void showPop() {
        View view = View.inflate(this, R.layout.pop_item, null);
        View bt_add = view.findViewById(R.id.bt_add);
        final AddDelView adv = view.findViewById(R.id.adv);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 60);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(rl, Gravity.BOTTOM, 0, 0);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加到购物车
                addCard(adv.getCount());
            }
        });
    }

    /**
     * 添加到购物车
     *
     * @param count
     */
    private void addCard(int count) {
        Map<String, String> params = new HashMap<>();
        params.put("key", getSharedPreferences("nsg", Context.MODE_PRIVATE).getString("key", ""));
        params.put("goods_id", goodsDetailBean.getDatas().getGoods_info().getGoods_id());
        params.put("quantity", count + "");
        httpUtil.doPost(Api.ADD_CARD, params, AddCardBean.class, new OnNetListener() {
            @Override
            public void onSuccess(BaseBean baseBean) throws IOException {
                popupWindow.dismiss();
            }

            @Override
            public void onError(IOException e) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_toCard:
                Intent intent = new Intent(GoodsDetailActivity.this, CardActivity.class);
                startActivity(intent);
                break;
        }
    }
}
