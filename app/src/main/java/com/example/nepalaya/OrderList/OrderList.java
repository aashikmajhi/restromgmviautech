package com.example.nepalaya.OrderList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.nepalaya.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class OrderList extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewpager;
    private TabItem tabpending, tabprocessing, tabcompleted, tabcancel;
    public PagerAdapter orderListAdapter;

    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.orderlist, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle("Order List");


        tabLayout = (TabLayout) rootview.findViewById(R.id.tablayout2);
        viewpager = rootview.findViewById(R.id.viewpager);
        tabpending = rootview.findViewById(R.id.pending_order);
        tabprocessing = rootview.findViewById(R.id.processing_order);
        tabcompleted = rootview.findViewById(R.id.completed_order);
        tabcancel = rootview.findViewById(R.id.cancel_order);


        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#F22727"));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#F22727"));

        orderListAdapter = new OrderListAdapter(getActivity().getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                , getContext(), tabLayout.getTabCount());
        viewpager.setAdapter(orderListAdapter);

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout)); // Added to check pages

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0) {
                    orderListAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    orderListAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                    orderListAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 3) {
                    orderListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());

            }
        });

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        return rootview;

    }
}
