package com.example.dell.appstore.activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.appstore.R;
import com.example.dell.appstore.adapter.spadapter;
import com.example.dell.appstore.model.sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activitysp extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    spadapter spadapter1;
    ArrayList<sanpham> listsp1;
    mhanler mhanler;
    int idsp ;
    int page=1;
    static boolean isloading = false;
    View footer;
    boolean limitdata = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitysp);
        Anhxa();
        ActionBar1();
        Getidsp();
        Loadsp(page);
        loadmoresp();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.giohang,menu);
        SearchView sv;
        sv = (SearchView) menu.findItem(R.id.search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                intent.putExtra("ten",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void ActionBar1() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadmoresp() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if(firstItem + visibleItem ==totalItem&&totalItem!=0&&isloading==false&&limitdata==false){
                    isloading = true;
                    thread thread = new thread();
                    thread.start();


                }

            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Activitysp.this,chitietsp.class);
                intent.putExtra("sp",listsp1.get(i));
                startActivity(intent);
            }
        });
    }

    private void Loadsp(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shoponline.atspace.cc/getsp.php?page="+String.valueOf(page), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idsp1;
                String tensp;
                String hinhsp;
                String mota;
                int giasp;
                int idloaisp;
                if(response!=null&&response.length()>2){
                    listView.removeFooterView(footer);
                    try{
                       JSONArray jsonArray = new JSONArray(response);
                       for(int i=0;i<jsonArray.length();i++){
                           JSONObject jsonObject = jsonArray.getJSONObject(i);
                           idsp1=jsonObject.getInt("id");
                           tensp = jsonObject.getString("ten");
                           hinhsp = jsonObject.getString("hinhanhsp");
                           giasp = jsonObject.getInt("gia");
                           mota = jsonObject.getString("motasp");
                           idloaisp = jsonObject.getInt("idloai");
                           listsp1.add(new sanpham(idsp,tensp,giasp,mota,hinhsp,idloaisp));
                           spadapter1.notifyDataSetChanged();
                       }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    limitdata = true;
                    listView.removeFooterView(footer);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("idloasp",String.valueOf(idsp));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Getidsp() {

        idsp = getIntent().getIntExtra("id",-1);

        switch (idsp){case 1:
              toolbar.setTitle("Điện thoại");
           break;
            case 2:
                toolbar.setTitle("Laptop");
                break;
            case 3:
                toolbar.setTitle("Tivi");
                break;
            case 4:
                toolbar.setTitle("Đồng hồ");
                break;
      }
    }

    private void Anhxa() {
        toolbar=(Toolbar) findViewById(R.id.toolbarsp);
        listsp1 = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listviewsp);
        spadapter1 = new spadapter(listsp1,getApplicationContext());
        listView.setAdapter(spadapter1);
        mhanler = new mhanler();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footer= inflater.inflate(R.layout.loadmore,null);

    }

    class mhanler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listView.addFooterView(footer);
                    break;
                case 1:
                    Loadsp(++page);
                    isloading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    class thread extends Thread{
        @Override
        public void run() {
            mhanler.sendEmptyMessage(0);
            try{
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message ms = mhanler.obtainMessage(1);
            mhanler.sendMessage(ms);
            super.run();
        }
    }
}
