package com.example.dell.appstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.appstore.R;
import com.example.dell.appstore.activity.GiohangActivity;
import com.example.dell.appstore.activity.MainActivity;
import com.example.dell.appstore.model.giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Dell on 06-Dec-17.
 */

public class giohang_adapter extends BaseAdapter {
    ArrayList<giohang> arrgiohang;
    Context context;

    public giohang_adapter(ArrayList<giohang> arrgiohang, Context context) {
        this.arrgiohang = arrgiohang;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrgiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrgiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class Viewholder{
        ImageView imggiohang;
        TextView txttengiohang,txtgiatrigiohang;
        Button btncong,btngsl,btntru;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Viewholder viewholder = null;
        if(view==null){
            viewholder= new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang,null);
            viewholder.txttengiohang = (TextView) view.findViewById(R.id.txttenhanggiohang);
            viewholder.txtgiatrigiohang = (TextView) view.findViewById(R.id.txtgiatrigiohang);
            viewholder.btngsl=(Button)view.findViewById(R.id.btnslgiohang);
            viewholder.btncong = (Button)view.findViewById(R.id.btnconggiohang);
            viewholder.btntru = (Button)view.findViewById(R.id.btntrugiohang);
            viewholder.imggiohang = (ImageView) view.findViewById(R.id.imggiohang);
            view.setTag(viewholder);
        }
        else {
            viewholder = (Viewholder) view.getTag();
        }
        giohang giohang = (com.example.dell.appstore.model.giohang) getItem(i);
        viewholder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewholder.txtgiatrigiohang.setText(decimalFormat.format(giohang.getGia())+" VNĐ");
        viewholder.btngsl.setText(giohang.getSoluong()+"");
        Picasso.with(context).load(giohang.getHinh()).placeholder(R.drawable.load).error(R.drawable.ero).into(viewholder.imggiohang);
        int sl = Integer.parseInt(viewholder.btngsl.getText().toString());
        if(sl>9){
            viewholder.btncong.setVisibility(View.INVISIBLE);
            viewholder.btntru.setVisibility(View.VISIBLE);
        }
        else if(sl<2){
            viewholder.btntru.setVisibility(View.INVISIBLE);
            viewholder.btncong.setVisibility(View.VISIBLE);
        }
        else if(sl>=2&sl<=9){
            viewholder.btntru.setVisibility(View.VISIBLE);
            viewholder.btncong.setVisibility(View.VISIBLE);
        }
        final Viewholder finalViewholder = viewholder;
        final Viewholder finalViewholder1 = viewholder;
        final Viewholder finalViewholder2 = viewholder;
        viewholder.btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(finalViewholder.btngsl.getText().toString())+1;
                int slcu= MainActivity.listgiohang.get(i).getSoluong();
                int giacu = MainActivity.listgiohang.get(i).getGia();
                int giamoi = (giacu/slcu)*slmoi;
                MainActivity.listgiohang.get(i).setSoluong(slmoi);
                MainActivity.listgiohang.get(i).setGia(giamoi);
                finalViewholder1.btngsl.setText(slmoi +"");
                DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
                finalViewholder.txtgiatrigiohang.setText(decimalFormat1.format(giamoi)+" VNĐ");
                GiohangActivity.Gettongtien();
                if(slmoi > 9){
                    finalViewholder2.btntru.setVisibility(View.VISIBLE);
                    finalViewholder2.btncong.setVisibility(View.INVISIBLE);
                    finalViewholder.txtgiatrigiohang.setText(decimalFormat1.format(giamoi)+" VNĐ");
                }else {
                    finalViewholder.btncong.setVisibility(View.VISIBLE);
                    finalViewholder2.btntru.setVisibility(View.VISIBLE);
                    finalViewholder.txtgiatrigiohang.setText(decimalFormat1.format(giamoi)+" VNĐ");
                }
            }
        });
        viewholder.btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(finalViewholder.btngsl.getText().toString())-1;
                int slcu= MainActivity.listgiohang.get(i).getSoluong();
                int giacu = MainActivity.listgiohang.get(i).getGia();
                int giamoi = (giacu/slcu)*slmoi;
                MainActivity.listgiohang.get(i).setSoluong(slmoi);
                MainActivity.listgiohang.get(i).setGia(giamoi);
                finalViewholder1.btngsl.setText(slmoi +"");
                DecimalFormat decimalFormat2 = new DecimalFormat("###,###,###");
                finalViewholder.txtgiatrigiohang.setText(decimalFormat2.format(giamoi)+" VNĐ");
                GiohangActivity.Gettongtien();
                if(slmoi<2){
                    finalViewholder2.btntru.setVisibility(View.INVISIBLE);
                    finalViewholder2.btncong.setVisibility(View.VISIBLE);
                    finalViewholder.txtgiatrigiohang.setText(decimalFormat2.format(giamoi)+" VNĐ");
                }else {
                    finalViewholder.btntru.setVisibility(View.VISIBLE);
                    finalViewholder2.btncong.setVisibility(View.VISIBLE);
                    finalViewholder.txtgiatrigiohang.setText(decimalFormat2.format(giamoi)+" VNĐ");
                }

            }
        });
        return view;
    }
}
