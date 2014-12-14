package com.mt178.handler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {
    private ListView lv_show;
    private ProgressDialog progressDialog;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_show = (ListView) findViewById(R.id.lv_show);
        progressDialog = new ProgressDialog(this);
        myAdapter = new MyAdapter(this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在下载,请稍候.............");
        new MyTask().execute();


    }

    public class MyTask extends AsyncTask<String, Void, List<Map<String, Object>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> maps) {
            super.onPostExecute(maps);
            myAdapter.setData(maps);
            lv_show.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }

        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            //连接网络，或缺json数据，并进行解析。
            //可以使用json，gson，fastjson
           /* try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    String jsonString = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("product");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();
//                        迭代输出json的key作为map的key
                        Iterator<String> iterator = jsonObject1.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            Object value = jsonObject1.get(key);
                            map.put(key, value);
                        }
                        list.add(map);


                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }*/

            for (int i = 0; i < 20; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", i);
                list.add(map);
            }


            return list;
        }
    }

    public class MyAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String, Object>> list = null;

        public MyAdapter(Context context) {
            this.context = context;
            layoutInflater = layoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.item, null);
            } else {
                view = convertView;
            }
            TextView name = (TextView) view.findViewById(R.id.tv_1);
            TextView address = (TextView) view.findViewById(R.id.tv_2);
            TextView price = (TextView) view.findViewById(R.id.tv_3);
            final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            final DownLoadImage downLoadImage = new DownLoadImage("http://www.sinaimg.cn/dy/slidenews/4_img/2013_27/704_1018074_974873.jpg");
            downLoadImage.loadImage(new DownLoadImage.ImageCallBack() {
                @Override
                public void getImage(Drawable drawable) {
                    imageView.setImageDrawable(drawable);
                }
            });
            name.setText(list.get(position).get("id").toString());

            return view;
        }

        public void setData(List<Map<String, Object>> list) {
            this.list = list;
        }
    }
}
