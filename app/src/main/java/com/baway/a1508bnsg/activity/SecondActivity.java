package com.baway.a1508bnsg.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.baway.a1508bnsg.R;
import com.baway.a1508bnsg.bean.User;
import com.baway.a1508bnsg.databinding.ActivitySecondBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SecondActivity extends BaseActivity {

    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

//        setContentView(R.layout.activity_second);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_second);
//
//        binding.setOnclick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(SecondActivity.this, "点击", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Subscribe(sticky = true)
    public void onReceive(User user) {
//        User user = new User("小明", "18");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        binding.setUser(user);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
