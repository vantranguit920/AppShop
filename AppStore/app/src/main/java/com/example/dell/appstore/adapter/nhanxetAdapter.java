package com.example.dell.appstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.appstore.R;
import com.example.dell.appstore.model.nhanxet;

import java.util.ArrayList;

/**
 * Created by Dell on 13-Dec-17.
 */

public class nhanxetAdapter extends BaseAdapter {
    Context context;
    ArrayList<nhanxet> listnx;

    public nhanxetAdapter(Context context, ArrayList<nhanxet> listnx) {
        this.context = context;
        this.listnx = listnx;
    }

    @Override
    public int getCount() {
        return listnx.size();
    }

    @Override
    public Object getItem(int i) {
        return listnx.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    class ViewHolder{
        TextView txttennx,txtnhanxet;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.dong_nhanxet,null);
            viewHolder.txttennx = (TextView) view.findViewById(R.id.txttennhanxet);
            viewHolder.txtnhanxet = (TextView) view.findViewById(R.id.txtnhanxet);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        nhanxet nx= (nhanxet) getItem(i);
        viewHolder.txttennx.setText(nx.getTen());
        viewHolder.txtnhanxet.setText(nx.getNhanxet());
        return view;
    }
}
