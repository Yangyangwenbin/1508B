package com.baway.a1508bnsg.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baway.a1508bnsg.R;
import com.baway.a1508bnsg.bean.BaseBean;
import com.baway.a1508bnsg.bean.LoginBean;
import com.baway.a1508bnsg.net.Api;
import com.baway.a1508bnsg.net.OnNetListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtName;
    private EditText mEtPwd;
    private Button mBtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mBtLogin = (Button) findViewById(R.id.bt_login);
        mBtLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                String name = mEtName.getText().toString().trim();
                String pwd = mEtPwd.getText().toString().trim();
                if (checkInfo(name, pwd)) {
                    showPd();
                    Map<String, String> params = new HashMap<>();
                    params.put("username", name);
                    params.put("password", pwd);
                    httpUtil.doPost(Api.LOGIN, params, LoginBean.class, new OnNetListener() {
                        @Override
                        public void onSuccess(BaseBean baseBean) throws IOException {
                            LoginBean loginBean = (LoginBean) baseBean;
                            //保存key值
                            SharedPreferences sp = getSharedPreferences("nsg", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("key", loginBean.getDatas().getKey());
                            edit.commit();

                            //跳转到主界面
                            dismissPd();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void onError(IOException e) {
                            dismissPd();
                        }
                    });
                }


                break;
        }
    }

    /**
     * 检查输入账号密码是否可用
     *
     * @return
     */
    private boolean checkInfo(String name, String pwd) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            toast("请输入账号密码");
            return false;
        }
        return true;
    }
}
