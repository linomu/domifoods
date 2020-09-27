package com.unicauca.domifoods.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.domain.Category;
import com.unicauca.domifoods.settings.CircleTransform;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.holderCategories> {
    /*Variables that we're gonna use*/
    ArrayList<Category> categories;
    Picasso mPicasso;

    public AdapterCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public AdapterCategories.holderCategories onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories , parent, false);
        mPicasso = new Picasso.Builder(view.getContext())
                .indicatorsEnabled(false)
                .build();
        return new holderCategories(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategories.holderCategories holder, int position) {
        holder.tv_name.setText(categories.get(position).getName());
        mPicasso.load(categories.get(position).getImage())
                .transform(new CircleTransform())
                .into(holder.img_category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class holderCategories extends RecyclerView.ViewHolder {
        ImageView img_category;
        TextView tv_name;
        public holderCategories(@NonNull View itemView) {
            super(itemView);
            img_category = itemView.findViewById(R.id.img_category);
            tv_name= itemView.findViewById(R.id.tv_name_category);
        }
    }
}
