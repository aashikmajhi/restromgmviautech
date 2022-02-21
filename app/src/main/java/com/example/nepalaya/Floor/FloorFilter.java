package com.example.nepalaya.Floor;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FloorFilter extends Filter {

    FloorAdapter floorAdapter;
    List<FloorModel> floorlistFIlter;

    public FloorFilter(FloorAdapter floorAdapter, List<FloorModel> floorlistFIlter) {
        this.floorAdapter = floorAdapter;
        this.floorlistFIlter = floorlistFIlter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults floorResults = new FilterResults();

        if (charSequence != null && charSequence.length() >0){
            charSequence = charSequence.toString().toUpperCase();
            ArrayList<FloorModel>  filteredFloor = new ArrayList<>();

            for (int i = 0; i< filteredFloor.size(); i++){
                if (filteredFloor.get(i).getFloor_name().toUpperCase().contains(charSequence)){
                    filteredFloor.add(filteredFloor.get(i));
                }
            }
            floorResults.count = filteredFloor.size();
            floorResults.values = filteredFloor;
        }else{
            floorResults.count = floorlistFIlter.size();
            floorResults.values = floorlistFIlter;
        }

        return floorResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        floorAdapter.floorModels = (ArrayList<FloorModel>) filterResults.values;
    }
}
