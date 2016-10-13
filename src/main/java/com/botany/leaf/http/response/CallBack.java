package com.botany.leaf.http.response;


/**
 * Created by shoyu666@163.com
 */

public interface CallBack <T>{
     void callbackHttpOk(int requestCode,T t);
}
