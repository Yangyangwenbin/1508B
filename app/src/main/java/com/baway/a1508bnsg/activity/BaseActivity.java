package com.baway.a1508bnsg.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.baway.a1508bnsg.net.HttpUtil;

import java.util.LinkedList;

/**
 * Created by peng on 2017/9/27.
 */

public class BaseActivity extends AppCompatActivity {
    private LinkedList<Activity> list = new LinkedList<>();
    protected HttpUtil httpUtil;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.add(this);
        httpUtil = HttpUtil.getInstance(this);
    }

    protected void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void finshAll() {
        for (Activity ac : list) {
            if (!ac.isFinishing()) {
                ac.finish();
            }
        }
    }

    /**
     * 显示进度条
     */
    protected void showPd() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加載...");
        dialog.show();
    }

    /**
     * 关闭进度条
     */
    protected void dismissPd() {
        if (dialog != null && !this.isFinishing()) {
            dialog.dismiss();
        }
    }
}
