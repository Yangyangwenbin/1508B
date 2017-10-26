package com.baway.a1508bnsg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.a1508bnsg.R;
import com.baway.a1508bnsg.bean.CardBean;
import com.baway.a1508bnsg.bean.MessageEvent;
import com.baway.a1508bnsg.bean.MsgMoneyCountEvent;
import com.baway.a1508bnsg.widget.AddDelView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by peng on 2017/10/20.
 */

public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CardBean.DatasBean.CartListBean.GoodsBean> list;

    public CardAdapter(Context context, List<CardBean.DatasBean.CartListBean.GoodsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CardBean.DatasBean.CartListBean.GoodsBean datasBean = list.get(position);
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.sdv.setImageURI(datasBean.getGoods_image_url());
        myViewHolder.tv_name.setText(datasBean.getGoods_name());
        myViewHolder.tv_price.setText(datasBean.getGoods_price());
        myViewHolder.cb.setChecked(datasBean.isChecked());

        myViewHolder.adv.setOnItemClick(new AddDelView.OnItemClick() {

            @Override
            public void onItemAddClick(int count) {
                if (datasBean.isChecked()) {
                    String goods_price = datasBean.getGoods_price();
                    float price = Float.parseFloat(goods_price);
                    MsgMoneyCountEvent msgMC = new MsgMoneyCountEvent();
                    msgMC.setNum(1);
                    msgMC.setMoney(price);
                    EventBus.getDefault().post(msgMC);
                } else {
                    Toast.makeText(context, "请先勾选", Toast.LENGTH_SHORT).show();
                    myViewHolder.adv.setCount();
                }
            }

            @Override
            public void onItemDelClick(int count) {
                if (datasBean.isChecked()) {
                    String goods_price = datasBean.getGoods_price();
                    float price = Float.parseFloat(goods_price);
                    MsgMoneyCountEvent msgMC = new MsgMoneyCountEvent();
                    msgMC.setNum(-1);
                    msgMC.setMoney(-price);
                    EventBus.getDefault().post(msgMC);
                }else{
                    Toast.makeText(context, "请先勾选", Toast.LENGTH_SHORT).show();
                    myViewHolder.adv.setCount();
                }
            }
        });

        myViewHolder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除该项
            }
        });
        //给checkbox设置点击事件
        myViewHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*先判断点击的checkbox是否是选中状态，如果是，
                则遍历其它checkbox,如果其它的checkbox都是选中状态则让全选被勾选上
                */
                if (myViewHolder.cb.isChecked()) {
                    datasBean.setChecked(true);
                    //把选中的数量和钱传到activity中
                    MsgMoneyCountEvent msgMC = new MsgMoneyCountEvent();
                    msgMC.setNum(1);
                    msgMC.setMoney(Float.parseFloat(datasBean.getGoods_price()));
                    EventBus.getDefault().post(msgMC);

                    if (isAllChecked()) {
                        //让全选被勾选上
                        MessageEvent msg = new MessageEvent();
                        msg.setChecked(true);
                        EventBus.getDefault().post(msg);
                    }
                } else {
                    //checkbox未选中状态
                    MsgMoneyCountEvent msgMC = new MsgMoneyCountEvent();
                    msgMC.setNum(-1);
                    msgMC.setMoney(-Float.parseFloat(datasBean.getGoods_price()));
                    EventBus.getDefault().post(msgMC);

                    datasBean.setChecked(false);
                    //让全选取消
                    MessageEvent msg = new MessageEvent();
                    msg.setChecked(false);
                    EventBus.getDefault().post(msg);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private boolean isAllChecked() {
        for (CardBean.DatasBean.CartListBean.GoodsBean goodsBean : list) {
            if (!goodsBean.isChecked()) {
                //表示有某个checkbox未选中
                return false;
            }
        }
        return true;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cb;
        private final SimpleDraweeView sdv;
        private final TextView tv_name;
        private final TextView tv_price;
        private final ImageView iv_del;
        private final AddDelView adv;

        public MyViewHolder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.cb);
            sdv = itemView.findViewById(R.id.sdv);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            iv_del = itemView.findViewById(R.id.iv_del);
            adv = itemView.findViewById(R.id.adv);
        }
    }


    public void refresh(List<CardBean.DatasBean.CartListBean.GoodsBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void cbChecked(boolean flag) {
        MsgMoneyCountEvent msg = new MsgMoneyCountEvent();
        msg.setFlag(true);
        EventBus.getDefault().post(msg);
        for (CardBean.DatasBean.CartListBean.GoodsBean goodsBean : list) {
            if (flag) {
                MsgMoneyCountEvent msgMC = new MsgMoneyCountEvent();
                msgMC.setNum(1);
                msgMC.setMoney(Float.parseFloat(goodsBean.getGoods_price()));
                EventBus.getDefault().post(msgMC);
            } else {
                MsgMoneyCountEvent msgMC = new MsgMoneyCountEvent();
                msgMC.setNum(-1);
                msgMC.setMoney(-Float.parseFloat(goodsBean.getGoods_price()));
                EventBus.getDefault().post(msgMC);
            }
            goodsBean.setChecked(flag);
            notifyDataSetChanged();
        }
    }


}
