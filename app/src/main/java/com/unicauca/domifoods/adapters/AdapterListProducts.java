package com.unicauca.domifoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.fragments.ShoppingcarFragment;
import com.unicauca.domifoods.modelsProduct.ProductShoppingCart;

import java.util.List;

//David Calle
public class AdapterListProducts extends RecyclerView.Adapter<AdapterListProducts.ViewHolder> {

    private List<ProductShoppingCart> products;
    private LayoutInflater pInflater;
    private Context context;
    Picasso mPicasso;
    //TextView sum;
    TextView sum2;
    private double sumTotal = 0;
    ShoppingcarFragment obj;


    public AdapterListProducts(List<ProductShoppingCart> ProductsList, Context context){
        this.pInflater = LayoutInflater.from(context);
        this.context = context;
        this.products = ProductsList;

    }

    @NonNull
    @Override
    //public AdapterListProducts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_products, parent, false);
        mPicasso = new Picasso.Builder(view.getContext())
                .indicatorsEnabled(false)
                .build();
        //return new AdapterListProducts.ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListProducts.ViewHolder holder, int position) {
        mPicasso.load(products.get(position).getUrlImagen())
                .fit()
                .placeholder(R.drawable.test)
                .into(holder.urlImagen);
        holder.binData(products.get(position));

    }


    public void setProducts(List<ProductShoppingCart> listproducs){
        products = listproducs;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView urlImagen,deleteImage;
        TextView name, price, cant, subtotal;
        Button btn_add,btn_minus,btn_add_shopping_cart;
        CardView card_view_product;
        public RelativeLayout ViewF, ViewB;

        ViewHolder(View itemView){
            super(itemView);
            btn_add = itemView.findViewById(R.id.btn_add);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            urlImagen = itemView.findViewById(R.id.img_producto);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.precio);
            cant = itemView.findViewById(R.id.cantidad);
            subtotal = itemView.findViewById(R.id.sub_total);
            ViewF = itemView.findViewById(R.id.rl);
            ViewB = itemView.findViewById(R.id.view_backgraund);

            card_view_product = itemView.findViewById(R.id.shopping_cv);

        }

        public void calcularTotal() {
            sumTotal = 0;
            for (ProductShoppingCart productShoppingCart : products) {
                sumTotal = sumTotal + productShoppingCart.getSubtotal();
            }
            //sum.setText("Total: $"+sumTotal);
            obj.etiquetado(sumTotal);

        }
        void binData(final ProductShoppingCart item){

            name.setText(item.getName());
            price.setText("$"+item.getPrice());
            cant.setText(""+item.getCant());
            subtotal.setText("Sub Total: "+item.getCant()*item.getPrice());

            calcularTotal();

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    item.setCant(item.getCant()+1);
                    cant.setText(""+item.getCant());
                    item.setSubtotal(item.getCant()*item.getPrice());
                    subtotal.setText("Sub Total: "+item.getSubtotal());
                    calcularTotal();

                }
            });

            btn_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //sum = view.findViewById(R.id.total_compra);
                    if(item.getCant() > 1){
                        item.setCant(item.getCant()-1);
                        cant.setText(""+item.getCant());
                        item.setSubtotal(item.getCant()*item.getPrice());
                        subtotal.setText("Sub Total: "+item.getSubtotal());
                        calcularTotal();

                    }

                }

            });

        }
    }
    @Override
    public int getItemCount() {

        if(products == null){
            return 0;
        }else{
            return products.size();
        }
    }


    public void prueba(ShoppingcarFragment aux){
        obj = aux;
    }

    //---------------Eliminar producto------------------//

    public void removeItem(int position){
        products.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(ProductShoppingCart item, int position){
        products.add(position,item);
        notifyItemInserted(position);
    }
}
