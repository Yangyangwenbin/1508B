package com.baway.a1508bnsg.net;

import com.baway.a1508bnsg.bean.BaseBean;

import java.io.IOException;

/**
 * Created by peng on 2017/9/27.
 */

public interface OnNetListener {
    public void onSuccess(BaseBean baseBean) throws IOException;

    public void onError(IOException e);
}
