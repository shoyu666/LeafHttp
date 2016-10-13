package com.botany.leaf.http.response;

import android.app.Dialog;
import android.util.Log;

import java.lang.ref.WeakReference;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by shoyo666@163.com
 */

public class CommonResponseSubscriber<T> extends Subscriber<T> {
    public static final String TAG = "CommonResponse";
    WeakReference<CallBack<T>> mWeekCallBack;
    WeakReference<Dialog> mWeekDialog;
    public int requestCode;

    public CommonResponseSubscriber bind(CallBack<T> callback) {
        mWeekCallBack = new WeakReference<>(callback);
        return this;
    }


    public CommonResponseSubscriber requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    @Override
    public void onCompleted() {
        Dialog d = getWeekDialog();
        if (d != null && d.isShowing()) {
            d.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException he = (HttpException) e;
            he.code();
            onCompleted();
        }
    }

    @Override
    public void onNext(T responseBody) {
        Log.e(TAG, "onNexton");
        CallBack callback = getCallBack();
        if (callback != null) {
            callback.callbackHttpOk(requestCode, responseBody);
        }
    }

    public CallBack getCallBack() {
        if (mWeekCallBack != null) {
            return mWeekCallBack.get();
        }
        return null;
    }

    public Dialog getWeekDialog() {
        if (mWeekDialog != null) {
            return mWeekDialog.get();
        }
        return null;
    }

    public CommonResponseSubscriber dialog(Dialog dialog) {
        if (dialog != null) {
            mWeekDialog = new WeakReference<>(dialog);
            dialog.show();
        }
        return this;
    }
}
