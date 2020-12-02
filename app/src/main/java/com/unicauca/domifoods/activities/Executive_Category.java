package com.unicauca.domifoods.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.CallBacks.MyItemTouchHelperCallback;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.adapters.AdapterCategories;
import com.unicauca.domifoods.adapters.AdapterSelectedProducts;
import com.unicauca.domifoods.domain.Category;
import com.unicauca.domifoods.domain.SelectProduct;
import com.unicauca.domifoods.interfaces.CallBackItemTouch;
import com.unicauca.domifoods.modelsCategory.CategoriesResponse;

import java.util.ArrayList;
import java.util.List;

public class Executive_Category extends AppCompatActivity implements CallBackItemTouch {

    Picasso mPicasso;
    RecyclerView recyclerView, recyclerViewSelectedProducts;
    ArrayList<CategoriesResponse> categories;
    List<SelectProduct> list_selected_products;
    RelativeLayout layout;
    TextView tv_drop;
    String platoSelecionado = "";
    ImageView img_restaurant_icon;
    AdapterSelectedProducts adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executive__category);
        /*Recycler Selected Products*/
        recyclerViewSelectedProducts = findViewById(R.id.recycler_products);
        layout = findViewById(R.id.layout_main_activity);//prueba
        list_selected_products = new ArrayList<>();///en este arreglo estan los productos a eliminar

        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new AdapterSelectedProducts(list_selected_products, getApplicationContext());
        //recyclerView.setAdapter(adapter);
        //ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);
        //ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //touchHelper.attachToRecyclerView(recyclerView);

        recyclerViewSelectedProducts.setOnDragListener(onDragListener);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSelectedProducts.setLayoutManager(layoutManager2);
        adapter = new AdapterSelectedProducts(list_selected_products, getApplicationContext());
        recyclerViewSelectedProducts.setAdapter(adapter);
        //David
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //touchHelper.attachToRecyclerView(recyclerView);
        touchHelper.attachToRecyclerView(recyclerViewSelectedProducts);

        //tv_drop = findViewById(R.id.tv_drop);
        //tv_drop.setOnDragListener(onDragListener);

        img_restaurant_icon = findViewById(R.id.img_restaurant_icon);
        mPicasso = new Picasso.Builder(getApplicationContext()).indicatorsEnabled(false).build();
        categories = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_categories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        categories.add(new CategoriesResponse(1, "Arroz Blanco", "Arroz Blanco", "https://d37k6lxrz24y4c.cloudfront.net/v2/es-mx/e9dc924f238fa6cc29465942875fe8f0/eeb27f8c-745c-4772-bfde-8a0bbe083666-600.jpg", "Fecha","State",true,1));
        categories.add(new CategoriesResponse(2, "Arroz con Verduras", "Arroz Blanco", "https://cookingmadehealthy.com/wp-content/uploads/2020/05/Cauliflower-Egg-Fried-Rice-1x1-1.jpg", "Fecha","State",true,1));
        categories.add(new CategoriesResponse(3, "Sancocho", "Arroz Blanco", "https://www.196flavors.com/wp-content/uploads/2018/10/sancocho-3-FP-500x500.jpg", "Fecha","State",true,1));
        categories.add(new CategoriesResponse(4, "Fríjoles", "Arroz Blanco", "https://cdn.kiwilimon.com/recetaimagen/3001/th5-640x640-10525.jpg", "Fecha","State",true,1));
        categories.add(new CategoriesResponse(5, "Fideos", "Arroz Blanco", "https://www.cilantroandcitronella.com/wp-content/uploads/2016/10/ramen_01_06-K-992x1352.jpg", "Fecha","State",true,1));
        categories.add(new CategoriesResponse(6, "Jugo de Mora", "Arroz Blanco", "https://puntacamaron.com.co/107/jugo-de-mora.jpg", "Fecha","State",true,1));
        categories.add(new CategoriesResponse(7, "Limonada", "Arroz Blanco", "https://t1.uc.ltmcdn.com/images/7/6/6/img_como_hacer_limonada_7667_600.jpg", "Fecha","State",true,1));

        AdapterCategories adapterCategories = new AdapterCategories(categories, this);
        recyclerView.setAdapter(adapterCategories);



        //mostrarData();
    }

    @Override
    public void itemTouchOnMode(int oldPosition, int newPosition) {
        list_selected_products.add(newPosition,list_selected_products.remove(oldPosition));
        adapter.notifyItemMoved(oldPosition,newPosition);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        String nombre = list_selected_products.get(viewHolder.getAdapterPosition()).getName();
        final SelectProduct deleteItem = list_selected_products.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();
        adapter.removeItem(viewHolder.getAdapterPosition());
        //¡Eliminar del plato! 😕
        Snackbar snackbar = Snackbar.make(layout, nombre +" Eliminado \uD83D\uDE15",4000);
        //Snackbar snackbar1 = Snackbar.make(layout," ",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("¿Devolver al plato?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.restoreItem(deleteItem,deletedIndex);
            }
        });
        snackbar.setActionTextColor(Color.CYAN);

        snackbar.show();
    }

    View.OnDragListener onDragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            int event = dragEvent.getAction();
            switch (event) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    dragEvent.getLocalState();

                    final View vista = (View) dragEvent.getLocalState();
                    if (vista.getId() == R.id.linear_element) {
                        //ClipData.Item item = dragEvent.getClipData().getItemAt(0);
                        Toast.makeText(Executive_Category.this, "Suéltalo! 😁", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Toast.makeText(Executive_Category.this, "¿Ya no lo quieres? 😕", Toast.LENGTH_SHORT).show();
                    break;
                case DragEvent.ACTION_DROP:
                    // Gets the item containing the dragged data
                    ClipData.Item item = dragEvent.getClipData().getItemAt(0);//obteniendo el id del producto

                    // Gets the text data from the item.
                    if (!isProductInList(item.getText().toString())) {
                        SelectProduct selectProduct = getProductById(item.getText().toString());
                        list_selected_products.add(selectProduct);
                        platoSelecionado += "🍵 " + selectProduct.getName() + "\n";
                        // Displays a message containing the dragged data.
                        //tv_drop.setText(platoSelecionado);

                        /*Agregar el objeto en el arreglo*/
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Executive_Category.this, "¿Doble porción? 🤔", Toast.LENGTH_SHORT).show();
                    }


                    break;
            }
            return true;
        }
    };

    private boolean isProductInList(String idProduct) {
        boolean isProductInList = false;
        for (SelectProduct selectProduct : list_selected_products) {
            if (selectProduct.getId() == Integer.parseInt(idProduct)) {
                isProductInList = true;
            }
        }
        return isProductInList;
    }

    public void mostrarData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterSelectedProducts(list_selected_products, getApplicationContext());
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private SelectProduct getProductById(String idProduct) {
        SelectProduct objProduct = null;
        for (CategoriesResponse category : categories) {
            if ((category.getId() == Integer.parseInt(idProduct))) {
                objProduct = new SelectProduct(category.getId(), category.getName());
                break;
            }
        }
        return objProduct;
    }
}