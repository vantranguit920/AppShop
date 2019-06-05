package com.example.dell.appstore.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.appstore.R;
import com.example.dell.appstore.adapter.nhanxetAdapter;
import com.example.dell.appstore.model.nhanxet;
import com.example.dell.appstore.model.sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MoreNhanxet extends AppCompatActivity {
    ListView lv;
    ArrayList<nhanxet> list;
    nhanxetAdapter adapter;
    sanpham sp;
    Toolbar tolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_nhanxet);
        Anhxa();
        sp = (sanpham) getIntent().getSerializableExtra("sp");
        Getnx();
        Actiontollbar();

    }

    private void Actiontollbar() {
        setSupportActionBar(tolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Getnx() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shoponline.atspace.cc/getnx.php?page=1", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                int id;
                int idsp;
                String ten;
                String nx;
                try {
                    if(response!=null)
                    {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject js = jsonArray.getJSONObject(i);
                            id = js.getInt("id");
                            idsp = js.getInt("idsp");
                            ten = js.getString("tennx");
                            nx = js.getString("nhanxet");
                            list.add(new nhanxet(id,idsp,ten,nx));
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                hashMap.put("idsp",String.valueOf(sp.getId()));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void Anhxa() {
        tolbar = (Toolbar) findViewById(R.id.toolbarnx);
        lv = (ListView) findViewById(R.id.listmorenx);
        list = new ArrayList<>();
        adapter = new nhanxetAdapter(getApplicationContext(),list);
        lv.setAdapter(adapter);

    }
}
