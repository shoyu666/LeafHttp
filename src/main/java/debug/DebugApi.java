package debug;

import android.app.Dialog;

import com.botany.leaf.http.request.RequestProxy;
import com.botany.leaf.http.response.CallBack;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by shoyu666@163.com
 */

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
