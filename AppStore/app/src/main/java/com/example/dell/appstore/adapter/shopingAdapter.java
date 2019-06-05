package com.example.dell.appstore.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dell.appstore.activity.dienthoaiFragment;

/**
 * Created by Dell on 10-Dec-17.
 */

public class shopingAdapter extends FragmentStatePagerAdapter {
    private  String listtitle[]={"Điện thoại","Láp Tóp","Đồng hồ","Ti Vi"};
    private dienthoaiFragment dienthoaiFragment;
    private  dienthoaiFragment laptopFragment;
    private dienthoaiFragment donghoFragment;
    private  dienthoaiFragment tiviFragment;
    public shopingAdapter(FragmentManager fm) {
        super(fm);
        laptopFragment = new dienthoaiFragment();
        dienthoaiFragment = new dienthoaiFragment();
        donghoFragment = new dienthoaiFragment();
        tiviFragment = new dienthoaiFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            Bundle bundle = new Bundle();
            bundle.putInt("id",1);
            dienthoaiFragment.setArguments(bundle);
            return dienthoaiFragment;
        } else if(position==1)
        {
            Bundle bundle = new Bundle();
            bundle.putInt("id",2);
            laptopFragment.setArguments(bundle);
            return laptopFragment;
        }
        else if(position==2)
        {
            Bundle bundle = new Bundle();
            bundle.putInt("id",4);
            donghoFragment.setArguments(bundle);
            return donghoFragment;
        }
        else if(position==3)
        {
            Bundle bundle = new Bundle();
            bundle.putInt("id",3);
            tiviFragment.setArguments(bundle);
            return tiviFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return listtitle.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listtitle[position];
    }
}
