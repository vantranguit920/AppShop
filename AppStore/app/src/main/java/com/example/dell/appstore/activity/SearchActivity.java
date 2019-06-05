package com.example.dell.appstore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.appstore.R;
import com.example.dell.appstore.adapter.spgiamgiaAdapter;
import com.example.dell.appstore.model.sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class SearchActivity extends AppCompatActivity {
    String ten;
    spgiamgiaAdapter adapter;
    ArrayList<sanpham> listsp;
    RecyclerView recyclerView;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ten = getIntent().getStringExtra("ten");
        Anhxa();
        Actiontoolbar();
        loadsp();

    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.giohang,menu);
        SearchView sv;
        sv = (SearchView) menu.findItem(R.id.search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ten = query;
                listsp.clear();
                adapter.notifyDataSetChanged();
                loadsp();
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

    private void loadsp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shoponline.atspace.cc/search.php?page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idsp1;
                String tensp;
                String hinhsp;
                String mota;
                int giasp;
                int idloaisp;
                int giamgia;
                if(response!=null&&response.length()>2){
                    try{
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idsp1=jsonObject.getInt("id");
                            tensp = jsonObject.getString("ten").trim();
                            hinhsp = jsonObject.getString("hinhanhsp");
                            giasp = jsonObject.getInt("gia");
                            mota = jsonObject.getString("motasp");
                            idloaisp = jsonObject.getInt("idloai");
                            giamgia = jsonObject.getInt("giamgia");
                            listsp.add(new sanpham(idsp1,tensp,giasp,mota,hinhsp,idloaisp,giamgia));
                            adapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
                else {
                    Toast.makeText(SearchActivity.this,"Không tìm thấy dữ liệu phù hợp!!",Toast.LENGTH_LONG).show();
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
                hashMap.put("ten",String.valueOf(ten));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Anhxa() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarsearch);
        listsp = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewsearch);
        adapter = new spgiamgiaAdapter(getApplicationContext(),listsp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapter);
    }
}
