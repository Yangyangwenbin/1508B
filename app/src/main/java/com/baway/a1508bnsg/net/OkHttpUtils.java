package com.baway.a1508bnsg.net;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by peng on 2017/10/11.
 * 使用懒汉式创建该对象，并保证线程安全
 */

public class OkHttpUtils implements IHttpListener {
    private static OkHttpUtils okHttpUtils;
    private final OkHttpClient httpClient;
    private final Handler handler;

    //构造方法私有化
    private OkHttpUtils() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpUtils getInstance() {
        if (okHttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpUtils == null) {
                    okHttpUtils = new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }


    /**
     * post请求
     *
     * @param url
     * @param params
     * @param clazz
     * @param onNetListener
     */
    public void doPost(String url, Map<String, String> params, Class clazz, OnNetListener onNetListener) {

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    public void doGet(String url, Class clazz, OnNetListener onNetListener) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        onNetListener.onSuccess();
                    }
                });
            }
        });
    }


    public void uploadFile(String url, String fileName) {
        String file = Environment.getExternalStorageState() + "/" + fileName;
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //创建RequestBody 设置类型等
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, fileBody).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void download(String url) {
        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }
}
