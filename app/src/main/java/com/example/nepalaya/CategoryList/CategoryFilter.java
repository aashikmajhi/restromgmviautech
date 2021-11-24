package com.example.nepalaya.CategoryList;

import android.widget.Filter;

import com.example.nepalaya.Table.TableModel;

import java.util.ArrayList;

public class CategoryFilter extends Filter {

    Category_Food_Adapter category_food_adapter;
    ArrayList<CategoryModel> filercatergory;

    public CategoryFilter(Category_Food_Adapter category_food_adapter, ArrayList<CategoryModel> filercatergory) {
        this.category_food_adapter = category_food_adapter;
        this.filercatergory = filercatergory;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if (charSequence != null && charSequence.length() > 0) {
            //CHANGE TO UPPER
            charSequence = charSequence.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<CategoryModel> filteredDate = new ArrayList<>();

            for (int i = 0; i < filercatergory.size(); i++) {
                //CHECK
                if (filercatergory.get(i).getCatName().toUpperCase().contains(charSequence)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredDate.add(filercatergory.get(i));
                }
            }

            results.count = filteredDate.size();
            results.values = filteredDate;
        } else {
            results.count = filercatergory.size();
            results.values = filercatergory;

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        // adapter.contactUsModels = (ArrayList<BivagcatMiddle>) filterResults.values;
        category_food_adapter.categoryModels = (ArrayList<CategoryModel>) filterResults.values;

        //REFRESH
        category_food_adapter.notifyDataSetChanged();
    }
}
