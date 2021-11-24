/*
package com.example.nepalaya.FoodHome;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class FoodFilter extends Filter {

    FoodAdapter foodAdapter;
    List<FoodModel> foodfilter;

    public FoodFilter(FoodAdapter foodAdapter, ArrayList<FoodModel> foodfilter) {
        this.foodAdapter = foodAdapter;
        this.foodfilter = foodfilter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results22= new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if (charSequence != null && charSequence.length() > 0) {
            //CHANGE TO UPPER
            charSequence = charSequence.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<FoodModel> filteredDate = new ArrayList<>();

            for (int i = 0; i < foodfilter.size(); i++) {
                //CHECK
                if (foodfilter.get(i).getProductName().toUpperCase().contains(charSequence)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredDate.add(foodfilter.get(i));
                }
            }
            results22.count = filteredDate.size();
            results22.values = filteredDate;
        } else {
            results22.count = foodfilter.size();
            results22.values = foodfilter;
        }
        return results22;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        foodAdapter.foodModels = (ArrayList<FoodModel>) filterResults.values;

        //REFRESH
        foodAdapter.notifyDataSetChanged();
    }
}
*/
