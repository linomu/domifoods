package com.unicauca.domifoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unicauca.domifoods.R;
import com.unicauca.domifoods.domain.SelectProduct;

import java.util.List;


public class AdapterSelectedProducts extends RecyclerView.Adapter<AdapterSelectedProducts.myHolder> {
    //0. Definir la lista donde voy a tener la información a mostrar
    private final List<SelectProduct> list_selected_products;
    //1. Definir dos variables más, la posición y el contexto
    private int posicion;
    private final Context contexto;

    //1.1 Crear el constructor para las dos variables creadas previamente
    public AdapterSelectedProducts(List<SelectProduct> list_selected_products, Context contexto) {
        this.list_selected_products = list_selected_products;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_product, parent, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        holder.tv_producto.setText("🍵" + list_selected_products.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if(list_selected_products == null){
            return 0;
        }else{
            return list_selected_products.size();
        }
    }

    public class myHolder extends RecyclerView.ViewHolder {
        TextView tv_producto;
        public RelativeLayout ViewF, ViewB;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            tv_producto = itemView.findViewById(R.id.tv_producto);
            ViewF = itemView.findViewById(R.id.rl);
            ViewB = itemView.findViewById(R.id.view_backgraund);
        }
    }

    public void removeItem(int position){
        list_selected_products.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(SelectProduct item, int position){
        list_selected_products.add(position,item);
        notifyItemInserted(position);
    }
}
