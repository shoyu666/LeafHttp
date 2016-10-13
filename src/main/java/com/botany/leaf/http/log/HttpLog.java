package com.botany.leaf.http.log;

import android.util.Log;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 日志
 * Created by shoyu666@163.com
 */

public class HttpLog implements Interceptor {
    public static final String TAG = "HttpLog";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.v(TAG, "request:" + request.toString());
        okhttp3.Response response = chain.proceed(chain.request());
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.v(TAG, "response body:" + content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
