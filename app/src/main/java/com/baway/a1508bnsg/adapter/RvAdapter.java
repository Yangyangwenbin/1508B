package com.baway.a1508bnsg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baway.a1508bnsg.R;
import com.baway.a1508bnsg.bean.RightChildBean;

import java.util.List;

/**
 * Created by peng on 2017/10/18.
 */

public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<RightChildBean.DatasBean.ClassListBean> list;
    private OnChildListener onChildListener;

    public interface OnChildListener {
        public void onChildItemClick(RightChildBean.DatasBean.ClassListBean classListBean);
    }

    public void setOnChildListener(OnChildListener onChildListener) {
        this.onChildListener = onChildListener;
    }

    public RvAdapter(Context context, List<RightChildBean.DatasBean.ClassListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv.setText(list.get(position).getGc_name());
        myViewHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChildListener != null) {
                    onChildListener.onChildItemClick(list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
