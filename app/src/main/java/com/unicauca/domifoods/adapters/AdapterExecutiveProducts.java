package com.unicauca.domifoods.adapters;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.apiUser.RetrofitClient;
import com.unicauca.domifoods.modelsCategory.CategoriesResponse;
import com.unicauca.domifoods.modelsProduct.ProductResponse;
import com.unicauca.domifoods.settings.CircleTransform;

import java.util.ArrayList;

public class AdapterExecutiveProducts extends RecyclerView.Adapter<AdapterExecutiveProducts.holderCategories> {
    /*Variables that we're gonna use for executive products */
    ArrayList<ProductResponse> products;
    Picasso mPicasso;
    Context context;

    public AdapterExecutiveProducts(ArrayList<ProductResponse> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    //methods p
    @Override
    public AdapterExecutiveProducts.holderCategories onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories , parent, false);
        mPicasso = new Picasso.Builder(view.getContext())
                .indicatorsEnabled(false)
                .build();
        return new holderCategories(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterExecutiveProducts.holderCategories holder, int position) {
        holder.tv_name.setText(products.get(position).getName());
        Log.i("foto","url categoria: "+products.get(position).getImage());
        mPicasso.load(RetrofitClient.url+products.get(position).getImage())
        //mPicasso.load(products.get(position).getImage())
                //.placeholder(R.drawable.test)
                .transform(new CircleTransform())
                .into(holder.img_category);


    }

    @Override
    //method get size for categories
    public int getItemCount() {
        return products.size();
    }

    public class holderCategories extends RecyclerView.ViewHolder {
        LinearLayout linear_element;
        ImageView img_category;
        TextView tv_name;
        public holderCategories(@NonNull View itemView) {
            super(itemView);
            img_category = itemView.findViewById(R.id.img_category);
            tv_name= itemView.findViewById(R.id.tv_name_category);
            linear_element = itemView.findViewById(R.id.linear_element);
            linear_element.setOnLongClickListener(longClickListener);
        }

        View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                //ClipData data = ClipData.newPlainText("",String.valueOf(categories.get(getLayoutPosition()).getName()));
                ClipData data = ClipData.newPlainText("",String.valueOf(products.get(getLayoutPosition()).getId()));
                View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data,myShadowBuilder,view,0);
                return true;
            }
        };
    }
}
