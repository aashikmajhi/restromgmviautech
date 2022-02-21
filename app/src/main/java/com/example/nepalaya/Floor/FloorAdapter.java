package com.example.nepalaya.Floor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.Floor.Listener.FloorOnClickListener;
import com.example.nepalaya.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FloorAdapter extends RecyclerView.Adapter<FloorAdapter.FloorViewHolder> implements Filterable {

    ArrayList<FloorModel> floorModels, floorModelsfilter;
    Context context;
    FloorFilter floorFilter;
    FloorOnClickListener floorlistener;

    public FloorAdapter(ArrayList<FloorModel> floorModels, Context context, FloorOnClickListener floorlistener) {
        this.floorModels = floorModels;
        this.floorModelsfilter = floorModels;
        this.context = context;
        this.floorlistener = floorlistener;
    }


    @NonNull
    @Override
    public FloorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View floorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_floor, parent,false);
        return new FloorViewHolder(floorView);
    }

    @Override
    public void onBindViewHolder(@NonNull FloorViewHolder holder, int position) {
         FloorModel floorModel = floorModels.get(position);
        holder.floor_name_desc.setText(floorModel.getFloor_name());

        holder.clicklayer_floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String floor_id = floorModel.getFloor_id();
                String floorName = floorModel.getFloor_name();

                getFloorDetails(floor_id, floorName);
            }
        });
    }

    private void getFloorDetails(String floor_id, String floorName) {
        floorlistener.onItemClick(floor_id,floorName);
    }

    @Override
    public int getItemCount() {
        return floorModels.size();
    }

    @Override
    public Filter getFilter(){
        if (floorFilter == null){
//            floorFilter = new FloorFilter(this,floorModelsfilter);
        }

        return floorFilter;
    }

    public class FloorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.floor_name_desc)
        TextView floor_name_desc;

        @BindView(R.id.clicklayer_floor)
        CardView clicklayer_floor;


        public FloorViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
