package com.unicauca.domifoods.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.activities.Executive_Category;
import com.unicauca.domifoods.apiUser.RetrofitClient;
import com.unicauca.domifoods.modelsCategory.CategoriesResponse;
import com.unicauca.domifoods.settings.CircleTransform;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class AdapterExecutiveCategories extends RecyclerView.Adapter<AdapterExecutiveCategories.holderCategories> {
    /*Variables that we're gonna use for executive categories */
    ArrayList<CategoriesResponse> executive_categories;
    CategoryListener listener;
    Context context;
    //Interface
    public interface CategoryListener{
        void categorySelected(int idCategory);
    }
    //Bind
    public void setListener(CategoryListener listener) {
        this.listener = listener;
    }

    public AdapterExecutiveCategories(ArrayList<CategoriesResponse> executive_categories, Context context) {
        this.executive_categories = executive_categories;
        this.context = context;
    }

    @NonNull
    //methods p
    @Override
    public AdapterExecutiveCategories.holderCategories onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_executive_categories , parent, false);
        return new holderCategories(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterExecutiveCategories.holderCategories holder, int position) {

        if(executive_categories.get(position).getId()==Executive_Category.ID_CATEGORY){
            holder.btn_executive_category.setBackgroundResource(R.drawable.bg_btn_category);
            holder.btn_executive_category.setTextColor(context.getResources().getColor(R.color.colorWhite));
        }else{
            holder.btn_executive_category.setBackgroundResource(R.drawable.border);
            holder.btn_executive_category.setTextColor(context.getResources().getColor(R.color.negro));
        }

        holder.btn_executive_category.setText(executive_categories.get(position).getName());
        holder.btn_executive_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null) {
                    Log.i("Lino", "Método on click. Listener != null. ID:"+executive_categories.get(position).getId());
                    listener.categorySelected(executive_categories.get(position).getId());
                }else{
                    Log.i("Lino", "Listener es null");
                }
            }
        });



    }

    @Override
    //method get size for categories
    public int getItemCount() {
        return executive_categories.size();
    }

    public class holderCategories extends RecyclerView.ViewHolder {
        Button btn_executive_category;
        public holderCategories(@NonNull View itemView) {
            super(itemView);
            btn_executive_category = itemView.findViewById(R.id.btn_executive_category);

        }

       

    }
}
