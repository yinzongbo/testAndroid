package cn.my1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    ListView myListView;
//    myAdaptor myselAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        List<ResolveInfo> infos = getAppInfos();
//        List<String> data = new ArrayList<String>();
//        data.add("apple");
//        data.add("banana");
//        data.add("perl");

        myListView.setAdapter(new myAdaptor(infos));
    }

    private void initView() {
        myListView = (ListView) findViewById(R.id.listOne);

    }

    /*
    * 获取当前所有的应用信息
    * */
    private List<ResolveInfo> getAppInfos(){
        Intent intent = new Intent(Intent.ACTION_MAIN,null);

        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        return getPackageManager().queryIntentActivities(intent,0);

    }

    class myAdaptor extends BaseAdapter{

        List<ResolveInfo> listData;

        //构造器传值
        public myAdaptor(List<ResolveInfo> inputData){
            listData = inputData;
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
            //return null;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //使用系统服务获将xml资源转换成view
            LayoutInflater layoutInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView=layoutInflator.inflate(R.layout.layout_item,null);

            TextView textview = convertView.findViewById(R.id.item_textview);
            ImageView imageView = convertView.findViewById(R.id.item_icon);

            textview.setText(listData.get(position).activityInfo.loadLabel(getPackageManager()));
//            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setImageDrawable(listData.get(position).activityInfo.loadIcon(getPackageManager()));

            return convertView;
        }
    }
}
