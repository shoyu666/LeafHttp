package com.botany.leaf.http.request;

import android.app.Dialog;
import android.support.annotation.NonNull;

import com.botany.leaf.http.cookie.MyCookieJar;
import com.botany.leaf.http.log.HttpLog;
import com.botany.leaf.http.response.CallBack;
import com.botany.leaf.http.response.CommonResponseSubscriber;
import com.botany.leaf.http.ssl.TrustAllSSL;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import rx.schedulers.Schedulers;

/**
 * Created by shoyu666@163.com
 */

public class RequestProxy {

    public static <T> void addRequest(@NonNull Observable<T> request, CallBack<T> callback, Dialog dialog, int requestCode) {
        CommonResponseSubscriber subscriber = new CommonResponseSubscriber().bind(callback).dialog(dialog).requestCode(requestCode);
        request.subscribeOn(Schedulers.io()).observeOn(rx.android.schedulers.AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    public static volatile Retrofit mRetrofit;


    private static Retrofit getSingleRetrofit() {
        if (mRetrofit == null) {
            synchronized (Retrofit.class) {
                if (mRetrofit == null) {
                    mRetrofit = getDefaultRetrofit();
                }
            }
        }
        return mRetrofit;
    }


    public static <T> T create(final Class<T> service) {
        return RequestProxy.getSingleRetrofit().create(service);
    }

    private static Retrofit getDefaultRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new MyCookieJar()).
                addInterceptor(new HttpLog()).sslSocketFactory(TrustAllSSL.getSSLSocketFactory()).hostnameVerifier(new TrustAllSSL.TrustAllHostnameVerifier()).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.baidu.com").addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client).build();
        return retrofit;
    }
}
