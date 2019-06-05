package com.example.dell.appstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.appstore.R;
import com.example.dell.appstore.activity.chitietsp;
import com.example.dell.appstore.model.sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Dell on 11-Dec-17.
 */

public class spgiamgiaAdapter extends RecyclerView.Adapter<spgiamgiaAdapter.itemholder> {
    Context context;
    ArrayList<sanpham> lissp;

    public spgiamgiaAdapter(Context context, ArrayList<sanpham> lissp) {
        this.context = context;
        this.lissp = lissp;
    }

    @Override
    public itemholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongsanphamsp,null);
        itemholder itemholder = new itemholder(v);
        return itemholder;
    }

    @Override
    public void onBindViewHolder(itemholder holder, int position) {
        sanpham sp = lissp.get(position);
        Picasso.with(context).load(sp.getHinh()).placeholder(R.drawable.load).error(R.drawable.ero).into(holder.imghinh);
        holder.txtensp.setText(sp.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("@@@,@@@,@@@");
        holder.txtgiaps.setText("Giá :" + decimalFormat.format(sp.getGiá())+ "VNĐ");
        holder.txtgiamgia.setText(sp.getGiamgia()+"%");
        if(sp.getGiamgia()==0){
            holder.linearLayoutgiamgia.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return lissp.size();
    }

    public class itemholder extends RecyclerView.ViewHolder{
        public ImageView imghinh;
        public TextView txtensp,txtgiaps,txtgiamgia;
        public LinearLayout linearLayoutgiamgia;

        public itemholder(View itemView) {
            super(itemView);
            imghinh = (ImageView) itemView.findViewById(R.id.imagehinhspshoping);
            txtensp = (TextView) itemView.findViewById(R.id.txttenspshoping);
            txtgiaps = (TextView) itemView.findViewById(R.id.txtgiashoping);
            txtgiamgia = (TextView) itemView.findViewById(R.id.txtgiamgia);
            linearLayoutgiamgia = (LinearLayout) itemView.findViewById(R.id.layoutgiamgia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,chitietsp.class);
                    intent.putExtra("sp",lissp.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }


}
