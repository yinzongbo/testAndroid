package cn.mytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button startDownLoadBtn;
    Button analysisDataBtn;
    MyListener mylistener;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mylistener = new MyListener();
        startDownLoadBtn = (Button) findViewById(R.id.bt1);
        analysisDataBtn = (Button) findViewById(R.id.bt2);
        startDownLoadBtn.setOnClickListener(mylistener);
        analysisDataBtn.setOnClickListener(mylistener);
    }


    //内部类的方式实现监听接口
    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt1:{

                    try {
                        //访问网络不能在主线程

                        URL url = new URL("https://www.duba.com/");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        //设置超时
                        connection.setConnectTimeout(30*1000);
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Content-Type","application/json");
                        connection.setRequestProperty("Charset","UTF-8");
                        connection.connect();//开始连接

                        int responsecode = connection.getResponseCode();
                        String response = connection.getResponseMessage();

                        //看看是否返回时200
                        if(responsecode == HttpURLConnection.HTTP_OK)
                        {
                            //获取流数据
                            InputStream inputStream = connection.getInputStream();

                            byte[] bytes = new byte[0];
                            //转换为字节流
                            bytes = new byte[inputStream.available()];
                            inputStream.read(bytes);
                            String str = new String(bytes);

                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG, "onClick: 开始被点击");
                    break;
                }
                case R.id.bt2:{
                    Log.d(TAG, "onClick: 解析被点击");
                    break;
                }
                default:
                    break;
            }
        }
    }
}
