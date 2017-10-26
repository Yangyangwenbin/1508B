package com.baway.a1508bnsg.utils;

import com.baway.a1508bnsg.net.Api;
import com.orhanobut.logger.Logger;

/**
 * Created by peng on 2017/9/28.
 */

public class LogUtil {
    public static void d(Object obj) {
        if (!Api.isOnline) {
            Logger.d(obj);
        }
    }
}
