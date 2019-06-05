package com.example.dell.appstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.appstore.R;
import com.example.dell.appstore.model.loai_sp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.ACCESSIBILITY_SERVICE;

/**
 * Created by Dell on 25-Nov-17.
 */

public class loai_spadapter extends BaseAdapter {
    ArrayList<loai_sp> arraylistloaisp;

    public loai_spadapter(ArrayList<loai_sp> arraylistloaisp, Context context) {
        this.arraylistloaisp = arraylistloaisp;
        this.context = context;
    }

    Context context;
    @Override
    public int getCount() {
        return arraylistloaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylistloaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class viewholder{
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewholder viewholder1;
        if(view == null)
        {
            viewholder1 = new viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_loaisp,null);
            viewholder1.textView = (TextView) view.findViewById(R.id.txtten);
            viewholder1.imageView = (ImageView) view.findViewById((R.id.imageview1));
            view.setTag(viewholder1);
        }else
        {
            viewholder1 = (viewholder) view.getTag();
        }
        loai_sp loaisp = (loai_sp) getItem(i);
        viewholder1.textView.setText(loaisp.getTen());
        Picasso.with(context).load(loaisp.getHinh()).placeholder(R.drawable.load).error(R.drawable.ero).into(viewholder1.imageView);

        return view;
    }
}
