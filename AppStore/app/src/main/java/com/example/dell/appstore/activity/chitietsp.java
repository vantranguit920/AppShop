package com.example.dell.appstore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.example.dell.appstore.adapter.nhanxetAdapter;
import com.example.dell.appstore.model.giohang;
import com.example.dell.appstore.model.nhanxet;
import com.example.dell.appstore.model.sanpham;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chitietsp extends AppCompatActivity {
    TextView txtten,txtgia,txtchitiet;
    ImageView imgctsp;
    Button btnthem,btnguinx,btnxemthem;
    LinearLayout linearLayout,linearlistnx;
    Spinner spinner;
    sanpham sp;
    Toolbar toolbar;
    ArrayList<nhanxet> listnx;
    nhanxetAdapter adapternx;
    ListView listViewnx;
    EditText edttennx,edtnhanxet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsp);
        Anhxa();
        getthongtin();
        btnthemAction();
        addspiner();
        Actionbar();
        getnhanxet();
        guinx();
        xemthem();
    }

    private void xemthem() {
        btnxemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chitietsp.this,MoreNhanxet.class);
                intent.putExtra("sp",sp);
                startActivity(intent);
            }
        });


    }

    private void guinx() {
        btnguinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idsp = String.valueOf(sp.getId());
                final String ten = String.valueOf(edttennx.getText());
                final String nhanxet = String.valueOf(edtnhanxet.getText());
                if(idsp.length()>0&&ten.length()>0&&nhanxet.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shoponline.byethost4.com/insertnhanxet.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(Integer.parseInt(response)>0){
                                listnx.clear();
                                adapternx.notifyDataSetChanged();
                                getnhanxet();
                                Toast.makeText(getApplicationContext(),"Đã thành công",Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hs = new HashMap<String, String>();
                            hs.put("ten",ten);
                            hs.put("idsanpham",idsp);
                            hs.put("nhanxet",nhanxet);
                            return hs;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Chưa điền thông tin",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getnhanxet() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shoponline.byethost4.com/getnx.php?page=1", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                int id;
                int idsp;
                String ten;
                String nx;
                int cout=0;
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
                            listnx.add(new nhanxet(id,idsp,ten,nx));
                            adapternx.notifyDataSetChanged();
                            cout++;
                        }
                        linearlistnx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));


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

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addspiner() {
        Integer[] sol = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,sol);
        spinner.setAdapter(arrayAdapter);
    }

    private void btnthemAction() {
       btnthem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int sl = Integer.parseInt(spinner.getSelectedItem().toString());
               boolean is = false;
               if(MainActivity.listgiohang.size()!=0){
                   for(int i=0;i<MainActivity.listgiohang.size();i++){
                       if(sp.getId()==MainActivity.listgiohang.get(i).getIdsp()){
                           MainActivity.listgiohang.get(i).setSoluong(MainActivity.listgiohang.get(i).getSoluong()+sl);
                           MainActivity.listgiohang.get(i).setGia((int) (MainActivity.listgiohang.get(i).getSoluong()*sp.getGiá()));
                           is= true;
                       }
                   }
                   if(is=false){
                       int gia= (int) (sl*sp.getGiá());
                       MainActivity.listgiohang.add(new giohang(sp.getId(),sp.getTen(),gia,sl,sp.getHinh()));

                   }

               }
               else{

                   int gia= (int) (sl*sp.getGiá());
                   MainActivity.listgiohang.add(new giohang(sp.getId(),sp.getTen(),gia,sl,sp.getHinh()));
               }
               Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
               startActivity(intent);
           }
       });

    }

    private void Anhxa() {
        btnxemthem = (Button) findViewById(R.id.btnxemthemnx);
        linearlistnx = (LinearLayout) findViewById(R.id.linearlistnx);
        edttennx = (EditText) findViewById(R.id.edttennhanxet);
        edtnhanxet = (EditText) findViewById(R.id.edtnhanxet);
        btnguinx = (Button) findViewById(R.id.btnguinhanxet);
        txtten = (TextView) findViewById(R.id.txttenctsp);
        txtgia = (TextView) findViewById(R.id.txtgiactsp);
        txtchitiet = (TextView) findViewById(R.id.txtchitietsp);
        imgctsp = (ImageView) findViewById(R.id.imgctsp);
        btnthem = (Button) findViewById(R.id.btnthem);
        spinner = (Spinner) findViewById(R.id.spinner);
        toolbar= (Toolbar) findViewById(R.id.toolbarctsp);
        listnx = new ArrayList<>();
        linearLayout = (LinearLayout) findViewById(R.id.linearlayoutnhanxet);
        adapternx = new nhanxetAdapter(getApplicationContext(),listnx);
        listViewnx = (ListView) findViewById(R.id.listviewdanhgia);
        listViewnx.setAdapter(adapternx);
    }

    public void getthongtin() {
        sp = (sanpham) getIntent().getSerializableExtra("sp");
        txtten.setText(sp.getTen());
        txtchitiet.setText(sp.getChitiet());
        DecimalFormat decimalFormat = new DecimalFormat("@@@,@@@,@@@");
        txtgia.setText("Giá: "+decimalFormat.format(sp.getGiá())+"VND");
        Picasso.with(getApplicationContext()).load(sp.getHinh()).placeholder(R.drawable.load).error(R.drawable.ero).into(imgctsp);
    }
}
