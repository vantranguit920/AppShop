package com.example.dell.appstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.appstore.R;
import com.example.dell.appstore.activity.chitietsp;
import com.example.dell.appstore.model.sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Dell on 28-Nov-17.
 */

public class sanpham_adapter extends RecyclerView.Adapter<sanpham_adapter.spholder> {
    Context context;

    public sanpham_adapter(Context context, ArrayList<sanpham> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<sanpham> list;


    @Override
    public spholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sanpham,null);
        spholder sp = new spholder(v);
        return sp;
    }

    @Override
    public void onBindViewHolder(spholder holder, int position) {
        sanpham sp = list.get(position);
        Picasso.with(context).load(sp.getHinh()).placeholder(R.drawable.load).error(R.drawable.ero).into(holder.imghinh);
        holder.txtensp.setText(sp.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("@@@,@@@,@@@");
        holder.txtgiaps.setText("Giá :" + decimalFormat.format(sp.getGiá())+ "VNĐ");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class spholder extends RecyclerView.ViewHolder{
        public ImageView imghinh;
        public TextView txtensp,txtgiaps;

        public spholder(View itemView) {
            super(itemView);
            imghinh = (ImageView) itemView.findViewById(R.id.imagehinhsp);
            txtensp = (TextView) itemView.findViewById(R.id.txttensp);
            txtgiaps = (TextView) itemView.findViewById(R.id.txtgia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,chitietsp.class);
                    intent.putExtra("sp",list.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }
}
