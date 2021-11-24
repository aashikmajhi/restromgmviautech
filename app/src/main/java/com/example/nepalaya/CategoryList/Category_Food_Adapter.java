package com.example.nepalaya.CategoryList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nepalaya.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Category_Food_Adapter extends RecyclerView.Adapter<Category_Food_Adapter.ViewHolder> {

    ArrayList<CategoryModel> categoryModels, categoryModelsfilter;
    private Context context;
    Category_Listener category_listener;
    CategoryFilter categoryFilter;

    public Category_Food_Adapter(ArrayList<CategoryModel> categoryModels, Context context, Category_Listener category_listener) {
        this.categoryModels = categoryModels;
        this.context = context;
        this.category_listener = category_listener;
        this.categoryModelsfilter = categoryModels;
    }

    @NonNull
    @Override
    public Category_Food_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_foodmenu_layout, parent, false);

        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull Category_Food_Adapter.ViewHolder holder, int position) {
        CategoryModel categoryModel = categoryModels.get(position);

        holder.tvFoodName.setText(categoryModel.CatName);

        Picasso.with(context).load(" https://i.pinimg.com/600x315/bc/54/75/bc5475484b6dfaf04a86a93d85378dfa.jpg").into(holder.tvFoodImamge);

        holder.tvFoodImamge.setImageResource(R.drawable.cake);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GotoFoodList(categoryModel.getCatID(), categoryModel.getCatName());
            }
        });

    }


    public void GotoFoodList(int Categoryid, String foodname) {
        category_listener.OnitemClicked(Categoryid, foodname);
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public Filter getFilter() {
        if (categoryFilter == null) {
            categoryFilter = new CategoryFilter(this, categoryModelsfilter);
        }
        return categoryFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_food_name)
        TextView tvFoodName;

        @BindView(R.id.iv_food_image)
        ImageView tvFoodImamge;

        @BindView(R.id.card_view)
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
