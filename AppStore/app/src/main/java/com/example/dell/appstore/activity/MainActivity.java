package com.example.dell.appstore.activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.dell.appstore.R;
import com.example.dell.appstore.adapter.loai_spadapter;
import com.example.dell.appstore.adapter.sanpham_adapter;
import com.example.dell.appstore.adapter.spgiamgiaAdapter;
import com.example.dell.appstore.model.giohang;
import com.example.dell.appstore.model.loai_sp;
import com.example.dell.appstore.model.sanpham;
import com.example.dell.appstore.until.checkconectwifi;
import com.example.dell.appstore.until.server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView,recyclerViewgiamgia;
    NavigationView navigationView;
    ListView listView2;
    ArrayList<loai_sp> listloaisp;
    loai_spadapter adapter;
    int id;
    String ten;
    String hinh;
    ArrayList<sanpham> listsp,listspgiamgia;
    sanpham_adapter adaptersp;
    spgiamgiaAdapter spAdapter;
    Button btnxemthem;

    int idsp;
    String tensp;
    String hinhsp;
    int giamgia;
    String mota;
    int giasp;
    int idloaisp;
    ImageButton btnbag;
    SearchView sv;
    public static ArrayList<giohang> listgiohang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(checkconectwifi.havenetwork(getApplicationContext())){
            setContentView(R.layout.activity_main);
            Anhxa();
            Navigationaction();
            ActionViewfliper();
            Getdataloaisp();
            Loadspmoi();
            listviewmenuAction();
            buttonaction();
            loadspgiamgia();

        }
        else {
            checkconectwifi.returnmessage(getApplicationContext(),"Hãy kết nối mạng");
        }

    }

    private void loadspgiamgia() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://shoponline.atspace.cc/getspgiamgia.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for(int i = 0;i<response.length();i++){
                        try{
                            JSONObject jsonObject = response.getJSONObject(i);
                            idsp=jsonObject.getInt("id");
                            giamgia = jsonObject.getInt("giamgia");
                            tensp = jsonObject.getString("ten").trim();
                            hinhsp = jsonObject.getString("hinhanhsp");
                            giasp = jsonObject.getInt("gia");
                            mota = jsonObject.getString("motasp");
                            idloaisp = jsonObject.getInt("idloai");
                            listspgiamgia.add(new sanpham(idsp,tensp,giasp,mota,hinhsp,idloaisp,giamgia));

                            spAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.giohang,menu);
        sv = (SearchView) menu.findItem(R.id.search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
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

    private void buttonaction() {
        btnbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,shoping.class);
                startActivity(intent);

            }
        });
        btnxemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,shoping.class);
                startActivity(intent);
            }
        });
    }

    private void listviewmenuAction() {
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this,Activitysp.class);
                        intent1.putExtra("id",listloaisp.get(i).getId());
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainActivity.this,Activitysp.class);
                        intent2.putExtra("id",listloaisp.get(i).getId());
                        startActivity(intent2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainActivity.this,Activitysp.class);
                        intent3.putExtra("id",listloaisp.get(i).getId());
                        startActivity(intent3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        Intent intent4 = new Intent(MainActivity.this,Activitysp.class);
                        intent4.putExtra("id",listloaisp.get(i).getId());
                        startActivity(intent4);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        Intent intent5 = new Intent(MainActivity.this,ThongtinActivity.class);
                        intent5.putExtra("id",listloaisp.get(i).getId());
                        startActivity(intent5);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        Intent intent6 = new Intent(MainActivity.this,LienheActivity.class);
                        intent6.putExtra("id",listloaisp.get(i).getId());
                        startActivity(intent6);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void Loadspmoi() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://shoponline.atspace.cc/getspmoi.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for(int i = 0;i<response.length();i++){
                        try{
                            JSONObject jsonObject = response.getJSONObject(i);
                            idsp=jsonObject.getInt("id");
                            tensp = jsonObject.getString("ten").trim();
                            hinhsp = jsonObject.getString("hinhanhsp");
                            giasp = jsonObject.getInt("gia");
                            mota = jsonObject.getString("motasp");
                            idloaisp = jsonObject.getInt("idloai");
                            listsp.add(new sanpham(idsp,tensp,giasp,mota,hinhsp,idloaisp));

                            adaptersp.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Getdataloaisp() {
        /*
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest("http://shoponline.atspace.cc/Getdataloaisp.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });*/
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://shoponline.atspace.cc/Getdataloaisp.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            ten = jsonObject.getString("tenloai").trim();
                            hinh = jsonObject.getString("hinh");
                            listloaisp.add(i+1,new loai_sp(id,ten,hinh));

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //checkconectwifi.returnmessage(getApplicationContext(),error.toString());

            }
        });


        requestQueue.add(jsonArrayRequest);
        //requestQueue.add(stringRequest);
        listloaisp.add(new loai_sp(0,"Thông tin","http://cdn1.iconfinder.com/data/icons/Pretty_office_icon_part_2/128/personal-information.png"));
        listloaisp.add(new loai_sp(0,"Liên hệ","https://hongngochospital.vn/wp-content/themes/flexible/assets/images/icon_phone_01.png"));

    }

    private void ActionViewfliper() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("http://novadesign.vn/wp-content/uploads/2016/09/anh-quang-cao-9.jpg");
        list.add("http://www.hisella.vn/wp-content/uploads/2016/02/12764731_1727299887507529_7329304196808002046_o.jpg");
        list.add("http://tintuc.vatgia.com/pictures/vnmedia/111216171806-156-723.jpg");
        list.add("https://media.laodong.vn/Uploaded/phanthiphuongthuy/2014_09_10/pa20_PZVF.jpg");
        for(int i =0;i<list.size();i++){
            ImageView img = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(list.get(i)).into(img);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(img);
        }
        ImageView imgv = new ImageView(getApplicationContext());
        imgv.setImageResource(R.drawable.quangcao);
        imgv.setScaleType(ImageView.ScaleType.FIT_XY);
        viewFlipper.addView(imgv);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        Animation anim_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in);
        Animation anim_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out);
        viewFlipper.setInAnimation(anim_in);
        viewFlipper.setOutAnimation(anim_out);
    }

    private void Navigationaction() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        btnxemthem = (Button) findViewById(R.id.btnxemthem);
        btnbag = (ImageButton) findViewById(R.id.btnsmall);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewFlipper =(ViewFlipper) findViewById(R.id.viewflipper);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        listView2 = (ListView) findViewById(R.id.listview2);
        listloaisp = new ArrayList<>();
        listloaisp.add(0,new loai_sp(0,"Trang Chính","http://image.talentnetwork.vn/dhmohcm///news/2016/06/15/1465982269_red-home-icon-png-htirvbbs3.png"));
        adapter = new loai_spadapter(listloaisp,getApplicationContext());
        listView2.setAdapter(adapter);
        listsp = new ArrayList<>();
        //
        recyclerViewgiamgia = (RecyclerView) findViewById(R.id.recyclerviewgiamgia);
        listspgiamgia = new ArrayList<>();
        spAdapter = new spgiamgiaAdapter(getApplicationContext(),listspgiamgia);
        recyclerViewgiamgia.setHasFixedSize(true);
        recyclerViewgiamgia.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,true));
        recyclerViewgiamgia.setAdapter(spAdapter);
        //

        adaptersp = new sanpham_adapter(getApplicationContext(),listsp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,true));
        recyclerView.setAdapter(adaptersp);
        if(listgiohang!=null){

        }
        else {
            listgiohang = new ArrayList<>();
        }


    }
}
