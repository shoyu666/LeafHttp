package debug;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.botany.leaf.http.R;
import com.botany.leaf.http.response.CallBack;


public class MainActivity extends Activity {
    TextView mTextView;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) this.findViewById(R.id.result);
        this.findViewById(R.id.getdata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugApi.DebugApiHelper.getData(1, new CallBack<Debug>() {
                    @Override
                    public void callbackHttpOk(int requestCode, Debug debug) {
                        //结果
                    }
                }, new Dialog(MainActivity.this), 1);
            }
        });
        this.findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mTextView.setText("");
            }
        });
    }

}
