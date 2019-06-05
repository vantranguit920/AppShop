package com.example.dell.appstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.appstore.R;
import com.example.dell.appstore.adapter.spadapter;
import com.example.dell.appstore.adapter.spgiamgiaAdapter;
import com.example.dell.appstore.model.sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by Dell on 10-Dec-17.
 */

public class dienthoaiFragment extends Fragment {

    spgiamgiaAdapter adapter;
    ArrayList<sanpham> listsp1;
    int idloai=1;
    LinearLayout ln;
    Button btnload;
    View loadview;
    View rootview;
    FrameLayout fml;
    RecyclerView recyclerView;
    boolean isloading=false;
    int page = 1;
    mHanler mHanler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.dienthoaifragment,container,false);
        loadview = inflater.inflate(R.layout.loadmore,null);
        Anhxa();
        btnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isloading ==false){
                    btnload.setVisibility(View.INVISIBLE);
                    isloading = true;
                    thread thread = new thread();
                    thread.start();
                }


            }
        });
        Bundle bundle = getArguments();
        if(bundle!=null){
            idloai = bundle.getInt("id");

        }
        loadsp(page);
        return rootview;
    }


    private void loadsp(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(rootview.getContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shoponline.atspace.cc/getsp.php?page="+String.valueOf(page), new Response.Listener<String>() {
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
                    btnload.setVisibility(View.VISIBLE);
                    fml.removeView(loadview);
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
                            listsp1.add(new sanpham(idsp1,tensp,giasp,mota,hinhsp,idloaisp,giamgia));

                            adapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
                else {
                    fml.removeView(loadview);
                    btnload.setVisibility(View.INVISIBLE);

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
                hashMap.put("idloasp",String.valueOf(idloai));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public class mHanler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    fml.addView(loadview);
                    break;
                case 1:
                    loadsp(++page);
                    isloading = false;
                    break;
            }
            super.handleMessage(msg);
            super.handleMessage(msg);
        }
    }
    public class thread extends Thread{
        @Override
        public void run() {
            mHanler.sendEmptyMessage(0);
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message ms = mHanler.obtainMessage(1);
            mHanler.sendMessage(ms);
            super.run();
        }
    }

    private void Anhxa() {
        fml = (FrameLayout) rootview.findViewById(R.id.frlayout);
        mHanler = new mHanler();
        btnload = (Button) rootview.findViewById(R.id.btnloadmore);
        ln = (LinearLayout) rootview.findViewById(R.id.linearlayoutsp);
        listsp1 = new ArrayList<>();
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerviewshoping);
        adapter = new spgiamgiaAdapter(rootview.getContext(),listsp1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(rootview.getContext(),2));
        recyclerView.setAdapter(adapter);

    }
}
