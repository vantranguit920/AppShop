package com.example.dell.appstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.appstore.R;
import com.example.dell.appstore.model.sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Dell on 29-Nov-17.
 */

public class spadapter extends BaseAdapter {
    ArrayList<sanpham> listsp;
    Context context;

    public spadapter(ArrayList<sanpham> listsp, Context context) {
        this.listsp = listsp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listsp.size();
    }

    @Override
    public Object getItem(int i) {
        return listsp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    class ViewHolder{
        ImageView ing;
        TextView txtten,txtgia;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.dongdienthoai,null);
            viewHolder.txtten = (TextView) view.findViewById(R.id.txttendienthoai);
            viewHolder.txtgia = (TextView) view.findViewById(R.id.txtgiadt);
            viewHolder.ing = (ImageView) view.findViewById(R.id.imgdienthoai);
            view.setTag(viewHolder);

        }
        else {viewHolder = (ViewHolder) view.getTag();}
        sanpham sp = (sanpham) getItem(i);
        viewHolder.txtten.setText(sp.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("@@@,@@@,@@@");
        viewHolder.txtgia.setText("Giá : " + decimalFormat.format(sp.getGiá())+"VNĐ");
        Picasso.with(context).load(sp.getHinh()).placeholder(R.drawable.load).error(R.drawable.ero).into(viewHolder.ing);

        return view;
    }
}
