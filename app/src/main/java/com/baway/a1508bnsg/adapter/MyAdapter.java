package com.baway.a1508bnsg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baway.a1508bnsg.R;
import com.baway.a1508bnsg.bean.ClassBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by peng on 2017/10/18.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ClassBean.DatasBean.ClassListBean> list;
    private OnItemListener onItemListener;

    public interface OnItemListener {
        public void onItemClick(ClassBean.DatasBean.ClassListBean classListBean);
    }

    public void setonItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public MyAdapter(Context context, List<ClassBean.DatasBean.ClassListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.left_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        String imagesUrl = list.get(position).getImage();
//        ImageLoader.getInstance().displayImage(imagesUrl, myViewHolder.iv);
        myViewHolder.iv.setImageURI(imagesUrl);
        myViewHolder.tv.setText(list.get(position).getGc_name());
        //给左侧条目设置点击事件
        myViewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemListener!=null){
                    onItemListener.onItemClick(list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView iv;
        private TextView tv;
        private LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (SimpleDraweeView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);
            ll = (LinearLayout) itemView.findViewById(R.id.ll);
        }
    }


}
