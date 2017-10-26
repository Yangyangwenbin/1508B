package com.baway.a1508bnsg.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.baway.a1508bnsg.R;
import com.baway.a1508bnsg.activity.GoodsListActivity;
import com.baway.a1508bnsg.bean.RightChildBean;

import java.util.List;

/**
 * Created by peng on 2017/10/18.
 */

public class MyExpandLvAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> group;
    private List<List<RightChildBean.DatasBean.ClassListBean>> child;

    public MyExpandLvAdapter(Context context, List<String> group, List<List<RightChildBean.DatasBean.ClassListBean>> child) {
        this.context = context;
        this.group = group;
        this.child = child;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.right_title_item, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(group.get(groupPosition));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.right_child_item, null);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(context, 3));
        RvAdapter rvAdapter = new RvAdapter(context, child.get(groupPosition));
        rv.setAdapter(rvAdapter);
        rvAdapter.setOnChildListener(new RvAdapter.OnChildListener() {
            @Override
            public void onChildItemClick(RightChildBean.DatasBean.ClassListBean classListBean) {
                String gc_id = classListBean.getGc_id();
                Intent intent = new Intent(context, GoodsListActivity.class);
                intent.putExtra("gcId", gc_id);
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
