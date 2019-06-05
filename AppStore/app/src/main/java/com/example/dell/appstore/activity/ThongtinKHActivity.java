package com.example.dell.appstore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.appstore.R;
import com.example.dell.appstore.until.checkconectwifi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongtinKHActivity extends AppCompatActivity {
    EditText editTextten,editTextsdt,editTextemail,editTextdiachi;
    Button btnxacnhan,btntrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_kh);
        Anhxa();
        if(checkconectwifi.havenetwork(getApplicationContext()))
        {
            buttonclick();
        }

    }

    private void buttonclick() {
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tenkh= editTextten.getText().toString().trim();
                final String sodt = editTextsdt.getText().toString().trim();
                final String dichi = editTextdiachi.getText().toString().trim();
                final String email = editTextemail.getText().toString().trim();
                if(tenkh.length()>0&&sodt.length()>0&&dichi.length()>0&&email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://shoponline.atspace.cc/insertkh.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response1) {
                            if(Integer.parseInt(response1)>0){
                                RequestQueue request = Volley.newRequestQueue(getApplicationContext());
                                StringRequest strequest = new StringRequest(Request.Method.POST, "http://shoponline.atspace.cc/insertctdonhang.php", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(Integer.parseInt(response)==1){
                                            MainActivity.listgiohang.clear();
                                            Toast.makeText(getApplicationContext(),"Đã thanh toán thành công",Toast.LENGTH_LONG).show();
                                            Intent in= new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(in);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),"Thanh toán thất bại",Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray js = new JSONArray();
                                        for(int i=0;i<MainActivity.listgiohang.size();i++){
                                            JSONObject object = new JSONObject();
                                            try {
                                                object.put("madonhang",response1);
                                                object.put("masanpham",MainActivity.listgiohang.get(i).getIdsp());
                                                object.put("tensp",MainActivity.listgiohang.get(i).getTensp());
                                                object.put("gia",MainActivity.listgiohang.get(i).getGia());
                                                object.put("soluong",MainActivity.listgiohang.get(i).getSoluong());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            js.put(object);
                                        }
                                        HashMap<String ,String> has = new HashMap<String, String>();
                                        has.put("json",js.toString());
                                        return has;
                                    }
                                };
                                request.add(strequest);
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
                            hs.put("tenkh",tenkh);
                            hs.put("sodtkh",sodt);
                            hs.put("diachikh",dichi);
                            hs.put("emailkh",email);

                            return hs;
                        }
                    };
                    requestQueue.add(stringRequest);

                }
                else {
                    Toast.makeText(getApplicationContext(),"Hãy kiểm tra lại dữ liệu",Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void Anhxa() {
        editTextdiachi = (EditText) findViewById(R.id.edtdiachikh);
        editTextten = (EditText) findViewById(R.id.edttenkh);
        editTextsdt=(EditText) findViewById(R.id.edtsdtkh);
        editTextemail = (EditText) findViewById(R.id.edtemail);
        btnxacnhan = (Button) findViewById(R.id.btnxacnhan);
        btntrove = (Button) findViewById(R.id.btntrove);
    }
}
