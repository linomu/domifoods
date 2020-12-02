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
import com.unicauca.domifoods.domain.Category;
import com.unicauca.domifoods.modelsCategory.CategoriesResponse;
import com.unicauca.domifoods.settings.CircleTransform;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.holderCategories> {
    /*Variables that we're gonna use for categories */
    ArrayList<CategoriesResponse> categories;
    Picasso mPicasso;

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

    public AdapterCategories(ArrayList<CategoriesResponse> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    //methods p
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
        Log.i("foto","url categoria: "+categories.get(position).getImage());
        //mPicasso.load(RetrofitClient.url+categories.get(position).getImage())

        holder.img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null) {
                    Log.i("Lino", "Método on click. Listener != null. ID:"+categories.get(position).getId());
                    listener.categorySelected(categories.get(position).getId());
                }else{
                    Log.i("Lino", "Listener es null");
                }
            }
        });
        mPicasso.load(categories.get(position).getImage())
                //.placeholder(R.drawable.test)
                .transform(new CircleTransform())
                .into(holder.img_category);


    }

    @Override
    //method get size for categories
    public int getItemCount() {
        return categories.size();
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
            img_category.setOnLongClickListener(longClickListener);
        }

        View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                //ClipData data = ClipData.newPlainText("",String.valueOf(categories.get(getLayoutPosition()).getName()));
                ClipData data = ClipData.newPlainText("",String.valueOf(categories.get(getLayoutPosition()).getId()));
                View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data,myShadowBuilder,view,0);
                return true;
            }
        };
    }
}
