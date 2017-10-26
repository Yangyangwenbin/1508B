package com.baway.a1508bnsg.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by peng on 2017/10/18.
 */

public class MyApp extends Application {
    private static final String APP_ID = "wx396ea2b17e2f8938";
    private IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        regToWx();
        Fresco.initialize(this);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(configuration);
    }

    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);
    }

    public IWXAPI getApi() {
        return api;
    }
}
