package com.example.dell.appstore.until;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.dell.appstore.activity.MainActivity;

/**
 * Created by Dell on 28-Nov-17.
 */

public class checkconectwifi  {
    public  static  boolean havenetwork(Context context){
        boolean havewifi = false;
        boolean havemobile= false;
        ConnectivityManager cn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nw = cn.getActiveNetworkInfo();
        if(nw!=null&&nw.isConnectedOrConnecting()){
            return true;
        }
        return  false;
    }

    public static void returnmessage(Context context,String thongbao){

            Toast.makeText(context,thongbao,Toast.LENGTH_LONG).show();

    }
}
