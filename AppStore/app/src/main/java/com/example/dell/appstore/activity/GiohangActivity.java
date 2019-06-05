package com.example.dell.appstore.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.appstore.R;
import com.example.dell.appstore.adapter.giohang_adapter;

import java.text.DecimalFormat;

public class GiohangActivity extends AppCompatActivity {

    ListView listView;
    static TextView txtgiatri;
    Button btntieptuc;
    Button btnthanhtoan;
    giohang_adapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        Gettongtien();
        Actionbar();
        listViewclick();
        buttonevent();
    }

    private void buttonevent() {
        btntieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.listgiohang.size()>0){
                    Intent intent = new Intent(getApplicationContext(),ThongtinKHActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Bạn chưa có mặt hàng nào để mua",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void listViewclick() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xác Nhận");
                builder.setMessage("Bạn có muốn xóa");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        MainActivity.listgiohang.remove(position);
                        adapter.notifyDataSetChanged();
                        Gettongtien();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.notifyDataSetChanged();
                        Gettongtien();
                    }
                });
                builder.show();
                return true;
            }
        });
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

    public static void Gettongtien() {
        int tongtien =0;
        for(int i=0;i<MainActivity.listgiohang.size();i++){
            tongtien = tongtien + MainActivity.listgiohang.get(i).getGia();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgiatri.setText(decimalFormat.format(tongtien)+"  VNĐ");
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbargiohang);
        listView =(ListView) findViewById(R.id.listviewgiohang);
        txtgiatri = (TextView) findViewById(R.id.txtgiastrigiohang);
        btntieptuc = (Button) findViewById(R.id.btntientucmuahang);
        btnthanhtoan = (Button) findViewById(R.id.btnthanhtoangiohang);
        toolbar = (Toolbar) findViewById(R.id.toolbargiohang);
        adapter = new giohang_adapter(MainActivity.listgiohang,getApplicationContext());
        listView.setAdapter(adapter);
    }
}
