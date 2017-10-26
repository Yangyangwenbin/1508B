package com.baway.a1508bnsg.net;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.baway.a1508bnsg.bean.BaseBean;
import com.baway.a1508bnsg.utils.NetWorkUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
 * Created by peng on 2017/9/27.
 */

public class HttpUtil {
    private static volatile HttpUtil httpUtil;
    private final OkHttpClient client;
    private Context context;
    private Handler handler = new Handler(Looper.getMainLooper());

    private HttpUtil(Context context) {
        //缓存目录
        File sdcache = new File(Environment.getExternalStorageDirectory(), "cache");
        int cacheSize = 10 * 1024 * 1024;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
//                .addNetworkInterceptor(new CacheInterceptor())
//                .writeTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(20, TimeUnit.SECONDS)
//                .cache(new Cache(sdcache,cacheSize))
                .build();
        this.context = context;

    }

    public static HttpUtil getInstance(Context context) {
        if (httpUtil == null) {
            synchronized (HttpUtil.class) {
                if (httpUtil == null) {
                    httpUtil = new HttpUtil(context);
                }
            }
        }
        return httpUtil;
    }

    public void doPost(String url, Map<String, String> params, final Class clazz, final OnNetListener onNetListener) {
        //网络判断
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, "没有网络，请查看设置", Toast.LENGTH_SHORT).show();
            return;
        }
        if (params != null && params.size() > 0) {
            FormBody.Builder builder = new FormBody.Builder();
            params.put("client", "android");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            FormBody formBody = builder.build();

            Request request = new Request.Builder().url(url).post(formBody).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final BaseBean baseBean = (BaseBean) new Gson().fromJson(response.body().string(), clazz);
                    int code = baseBean.getCode();
                    if (code == 200) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    onNetListener.onSuccess(baseBean);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (code == 400) {

                    } else if (code == 300) {

                    }
                }
            });
        }


    }

    public void download(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);

    }

    public void doGet(String url, final Class clazz, final OnNetListener onNetListener) {
        //网络判断
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, "没有网络，请查看设置", Toast.LENGTH_SHORT).show();
            return;
        }
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        final Request request = builder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onError(e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final BaseBean baseBean = (BaseBean) new Gson().fromJson(string, clazz);
                int code = baseBean.getCode();
                if (code == 200) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                onNetListener.onSuccess(baseBean);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else if (code == 400) {

                } else if (code == 300) {

                }
            }
        });
    }

    /**
     * @param url
     * @param params
     * @param header
     * @param clazz
     * @param onNetListener
     */
    public void doGet(String url, HashMap<String, String> params, HashMap<String, String> header, final Class clazz, final OnNetListener onNetListener) {

        //网络判断
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, "没有网络，请查看设置", Toast.LENGTH_SHORT).show();
            return;
        }

        Request.Builder builder = new Request.Builder();
        if (params != null && params.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(url);
                sb.append("?");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
            url = sb.toString();
        }
        builder.url(url);
        //添加请求头
        if (params != null && header.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        final Request request = builder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onError(e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final BaseBean baseBean = (BaseBean) new Gson().fromJson(string, clazz);
                int code = baseBean.getCode();
                if (code == 200) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                onNetListener.onSuccess(baseBean);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else if (code == 400) {

                } else if (code == 300) {

                }
            }
        });
    }

    /**
     * 上传
     *
     * @param url
     * @param fileName
     */
    public void uploadFile(String url, String fileName) {
        String file = Environment.getExternalStorageState() + "/" + fileName;
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //创建RequestBody 设置类型等
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, fileBody).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 下载
     *
     * @param url
     */
    public void download(String url) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }
}
