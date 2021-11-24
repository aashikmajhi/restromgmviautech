package com.example.nepalaya.OrderList;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nepalaya.OrderList.OrderTabs.CancelOrder;
import com.example.nepalaya.OrderList.OrderTabs.CompletedOrder;
import com.example.nepalaya.OrderList.OrderTabs.PendingOrder;
import com.example.nepalaya.OrderList.OrderTabs.ProcessingOrder;

public class OrderListAdapter extends FragmentPagerAdapter {

    private int numeroftab;
    Context context;

    public OrderListAdapter(@NonNull FragmentManager fm, int behavior, Context context, int numeroftab) {
        super(fm, behavior);
        this.context = context;
        this.numeroftab = numeroftab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new PendingOrder();
        } else if (position == 1) {
            return new ProcessingOrder();
        } else if (position == 2) {
            return new CompletedOrder();
        } else if (position == 3) {
            return new CancelOrder();
        } else {
            return null;
        }


    }

    @Override
    public int getCount() {
        return numeroftab;  // It Counts the total number of tabs
    }

/*    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }*/
}
