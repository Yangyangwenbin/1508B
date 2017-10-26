package com.baway.a1508bnsg.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ExpandableListView;

import com.baway.a1508bnsg.R;
import com.baway.a1508bnsg.adapter.MyAdapter;
import com.baway.a1508bnsg.adapter.MyExpandLvAdapter;
import com.baway.a1508bnsg.bean.BaseBean;
import com.baway.a1508bnsg.bean.ClassBean;
import com.baway.a1508bnsg.bean.RightChildBean;
import com.baway.a1508bnsg.bean.RightTitleBean;
import com.baway.a1508bnsg.net.Api;
import com.baway.a1508bnsg.net.OnNetListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {


    private RecyclerView mRv;
    private ExpandableListView mElv;
    private List<String> group = new ArrayList<>();
    private List<List<RightChildBean.DatasBean.ClassListBean>> child = new ArrayList<>();
    private int titlesize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getLeft();


    }


    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.rv);
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    /**
     * 获取分类
     */
    private void getLeft() {
        httpUtil.doGet(Api.CLASS, ClassBean.class, new OnNetListener() {
            @Override
            public void onSuccess(BaseBean baseBean) throws IOException {
                ClassBean bean = (ClassBean) baseBean;
                MyAdapter adapter = new MyAdapter(MainActivity.this, bean.getDatas().getClass_list());
                mRv.setAdapter(adapter);
                getRightTitle(bean.getDatas().getClass_list().get(0).getGc_id());
                adapter.setonItemListener(new MyAdapter.OnItemListener() {
                    @Override
                    public void onItemClick(ClassBean.DatasBean.ClassListBean classListBean) {
                        String gc_id = classListBean.getGc_id();
                        getRightTitle(gc_id);
                    }
                });
            }

            @Override
            public void onError(IOException e) {

            }
        });
    }

    /**
     * 右侧一级列表的数据
     *
     * @param gcId
     */
    private void getRightTitle(String gcId) {
        String url = String.format(Api.RIGHT, gcId);
        httpUtil.doGet(url, RightTitleBean.class, new OnNetListener() {
            @Override
            public void onSuccess(BaseBean baseBean) throws IOException {
                //为了防止添加重复的数据
                if (group.size() > 0) {
                    group.clear();
                }
                //为了防止重复数据
                if (child.size() > 0) {
                    child.clear();
                }
                RightTitleBean rightTitleBean = (RightTitleBean) baseBean;
                titlesize = rightTitleBean.getDatas().getClass_list().size();
                for (int i = 0; i < titlesize; i++) {
                    group.add(rightTitleBean.getDatas().getClass_list().get(i).getGc_name());
                    String gc_id =
                            rightTitleBean.getDatas().getClass_list().get(i).getGc_id();
                    getRightItem(gc_id);
                }
            }


            @Override
            public void onError(IOException e) {

            }
        });

    }

    private void getRightItem(String gc_id) {
        String url = String.format(Api.RIGHT, gc_id);
        httpUtil.doGet(url, RightChildBean.class, new OnNetListener() {
            @Override
            public void onSuccess(BaseBean baseBean) throws IOException {

                RightChildBean rightChildBean = (RightChildBean) baseBean;
                int size = rightChildBean.getDatas().getClass_list().size();
                List<RightChildBean.DatasBean.ClassListBean> l = new ArrayList<RightChildBean.DatasBean.ClassListBean>();
                for (int i = 0; i < size; i++) {
                    String gc_name = rightChildBean.getDatas().getClass_list().get(i).getGc_name();
                    String gcId = rightChildBean.getDatas().getClass_list().get(i).getGc_id();
                    RightChildBean.DatasBean.ClassListBean classListBean = new RightChildBean.DatasBean.ClassListBean();
                    classListBean.setGc_name(gc_name);
                    classListBean.setGc_id(gcId);
                    l.add(classListBean);
                }
                child.add(l);
                if (child.size() == titlesize) {
                    MyExpandLvAdapter myExpandLvAdapter = new MyExpandLvAdapter(MainActivity.this, group, child);
                    mElv.setAdapter(myExpandLvAdapter);
                }
            }

            @Override
            public void onError(IOException e) {

            }
        });
    }
}
