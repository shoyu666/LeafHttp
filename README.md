# LeafHttp
#### 功能
```

retrofit2 + OkHttp + RxAndroid 封装
```

#### 使用
```
定义http 接口
public interface DebugApi {

    @GET
    Observable<Debug> getData(@Query("id") int id);

    class DebugApiHelper {
        public static void getData(int id, CallBack<Debug> callback, Dialog dialog, int requestCode) {
            Observable<Debug> request = RequestProxy.create(DebugApi.class).getData(id);
            RequestProxy.addRequest(request, callback, dialog, requestCode);
        }
    }
}
```

```
调用
@Override
            public void onClick(View view) {
                DebugApi.DebugApiHelper.getData(1, new CallBack<Debug>() {
                    @Override
                    public void callbackHttpOk(int requestCode, Debug debug) {
                        //结果
                    }
                }, new Dialog(MainActivity.this), 1);
            }
```
