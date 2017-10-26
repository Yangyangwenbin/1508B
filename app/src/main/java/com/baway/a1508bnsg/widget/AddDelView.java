package com.baway.a1508bnsg.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baway.a1508bnsg.R;

/**
 * Created by peng on 2017/10/20.
 */

public class  AddDelView extends LinearLayout {
    private int count = 1;
    private TextView num;
    private OnItemClick onItemClick;

    public interface OnItemClick {
        public void onItemAddClick(int count);

        public void onItemDelClick(int count);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public AddDelView(Context context) {
        this(context, null);
    }

    public AddDelView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.add_del_view, this);
        TextView add = findViewById(R.id.add);
        TextView del = findViewById(R.id.del);
        num = findViewById(R.id.num);

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(++count + "");
                onItemClick.onItemAddClick(1);
            }
        });

        del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count != 1) {
                    count = --count;
                    onItemClick.onItemDelClick(-1);
                }
                num.setText(count >= 1 ? count + "" : 1 + "");

            }
        });
    }

    /**
     * 获取商品数量
     *
     * @return
     */
    public int getCount() {
        return count;
    }

    public void setCount() {
        count = 1;
        num.setText(count + "");

    }
}
