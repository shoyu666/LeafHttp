package com.botany.leaf.http.cookie;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 维护sesssion
 * Created by shoyo666@163.com
 */

public class MyCookieJar implements CookieJar {
    public static final String TAG = "MyCookieJar";
    private static final String JSESSIONID = "JSESSIONID";
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static Cookie JESSIONCOOKIE;

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() != 0) {
            for (Cookie c : cookies) {
                if (!TextUtils.isEmpty(c.domain()) && c.domain().equals(url.host()) && JSESSIONID.equals(c.name())) {
                    JESSIONCOOKIE = c;
                    Log.e(TAG, "saveFromResponse " + c.toString() + url.toString());
                }
            }
        }
        cookieStore.put(url.host(), cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url.host());
        List<Cookie> temp = new ArrayList<>();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (!TextUtils.isEmpty(c.domain()) && c.domain().equals(url.host()) && JSESSIONID.equals(c.name())) {
                    //pre session
                } else {
                    temp.add(c);
                }
            }
        }
        if (JESSIONCOOKIE != null)
            temp.add(JESSIONCOOKIE);
        return temp;
    }
}
