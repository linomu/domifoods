package com.unicauca.domifoods.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.unicauca.domifoods.adapters.AdapterExecutiveCategories;
import com.unicauca.domifoods.adapters.AdapterExecutiveProducts;
import com.unicauca.domifoods.adapters.AdapterProducts;
import com.unicauca.domifoods.adapters.AdapterSelectedProducts;
import com.unicauca.domifoods.apiUser.RetrofitClient;
import com.unicauca.domifoods.domain.Category;
import com.unicauca.domifoods.domain.Product;
import com.unicauca.domifoods.domain.SelectProduct;
import com.unicauca.domifoods.interfaces.CallBackItemTouch;
import com.unicauca.domifoods.modelsCategory.CategoriesResponse;
import com.unicauca.domifoods.modelsProduct.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.unicauca.domifoods.fragments.ProductsFragment.ID_RESTAURANT;

public class Executive_Category extends AppCompatActivity implements CallBackItemTouch {

    Picasso mPicasso;
    RecyclerView recyclerViewCategories, recyclerViewSelectedProducts, recyclerViewProducts;
    ArrayList<ProductResponse> products;
    ArrayList<CategoriesResponse> categories;
    List<SelectProduct> list_selected_products;
    RelativeLayout layout;
    public static int ID_CATEGORY = 0;
    TextView tv_drop;
    String platoSelecionado = "";
    ImageView img_restaurant_icon;
    AdapterSelectedProducts adapter;
    private ProgressDialog progDailogProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executive__category);
        /*Recycler Selected Products*/
        recyclerViewSelectedProducts = findViewById(R.id.recycler_products);
        layout = findViewById(R.id.layout_main_activity);//prueba
        list_selected_products = new ArrayList<>();///en este arreglo estan los productos a eliminar


        /*Recycler view para las categorias*/
        recyclerViewCategories = findViewById(R.id.recycler_executive_categories);
        LinearLayoutManager linearLayoutManagerCategories =  new LinearLayoutManager(Executive_Category.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategories.setLayoutManager(linearLayoutManagerCategories);
        //adapter = new AdapterSelectedProducts(list_selected_products, getApplicationContext());
        //recyclerView.setAdapter(adapter);
        //ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);
        //ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //touchHelper.attachToRecyclerView(recyclerView);

        recyclerViewSelectedProducts.setOnDragListener(onDragListener);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(Executive_Category.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSelectedProducts.setLayoutManager(layoutManager2);
        adapter = new AdapterSelectedProducts(list_selected_products, Executive_Category.this);
        recyclerViewSelectedProducts.setAdapter(adapter);
        //David
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //touchHelper.attachToRecyclerView(recyclerView);
        touchHelper.attachToRecyclerView(recyclerViewSelectedProducts);

        //tv_drop = findViewById(R.id.tv_drop);
        //tv_drop.setOnDragListener(onDragListener);

        img_restaurant_icon = findViewById(R.id.img_restaurant_icon);
        mPicasso = new Picasso.Builder(Executive_Category.this).indicatorsEnabled(false).build();
        products = new ArrayList<>();
        recyclerViewProducts = findViewById(R.id.recycler_executive_products);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Executive_Category.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProducts.setLayoutManager(layoutManager);

        fillOutTheCategories();

        //Option 1
       // recoverExecutiveProductsByApis(3);

        //Option2
        /*
        //int id, String name, String description, String image, String date_creation, int price, int category, boolean state_delete, boolean state_disponibility
        products.add(new ProductResponse(1, "Arroz Blanco", "Arroz Blanco", "https://d37k6lxrz24y4c.cloudfront.net/v2/es-mx/e9dc924f238fa6cc29465942875fe8f0/eeb27f8c-745c-4772-bfde-8a0bbe083666-600.jpg", "Fecha",0,1,true, true));
        products.add(new ProductResponse(2, "Arroz con Verduras", "Arroz Blanco", "https://cookingmadehealthy.com/wp-content/uploads/2020/05/Cauliflower-Egg-Fried-Rice-1x1-1.jpg", "Fecha",0,1,true, false));
        products.add(new ProductResponse(3, "Sancocho", "Arroz Blanco", "https://www.196flavors.com/wp-content/uploads/2018/10/sancocho-3-FP-500x500.jpg", "Fecha",0,1,true, false));
        products.add(new ProductResponse(4, "Fríjoles", "Arroz Blanco", "https://cdn.kiwilimon.com/recetaimagen/3001/th5-640x640-10525.jpg", "Fecha",0,1,true, false));
        products.add(new ProductResponse(5, "Fideos", "Arroz Blanco", "https://www.cilantroandcitronella.com/wp-content/uploads/2016/10/ramen_01_06-K-992x1352.jpg", "Fecha",0,1,true, false));
        products.add(new ProductResponse(6, "Jugo de Mora", "Arroz Blanco", "https://puntacamaron.com.co/107/jugo-de-mora.jpg", "Fecha",0,1,true, false));
        products.add(new ProductResponse(7, "Limonada", "Arroz Blanco", "https://t1.uc.ltmcdn.com/images/7/6/6/img_como_hacer_limonada_7667_600.jpg", "Fecha",0,1,true, false));

        AdapterExecutiveProducts adapterExecutive Products = new AdapterExecutiveProducts(products, this);
        recyclerViewProducts.setAdapter(adapterExecutiveProducts);
         */

    }

    public void fillOutTheCategories() {
        //Servicio web
        categories = new ArrayList<>();
        //startProgressDialogCategory();
        Call<List<CategoriesResponse>> call = RetrofitClient.getInstance(getApplicationContext()).getApi().getExecutiveCategoriesByRestaurantId(ID_RESTAURANT);
        call.enqueue(new Callback<List<CategoriesResponse>>() {
            @Override
            public void onResponse(Call<List<CategoriesResponse>> call, Response<List<CategoriesResponse>> response) {
                Log.i("Lino", "I'm inside OnResponse executive categories");
                if (response.isSuccessful()) {
                    Log.i("test", "The response was successful. Code: " + response.code());
                    List<CategoriesResponse> categoriesByResponse = response.body();

                    int position = 0;
                    for (CategoriesResponse category : categoriesByResponse) {
                        Log.i("Lino", category.toString());
                        categories.add(new CategoriesResponse(category.getId(), category.getName(), category.getDescription(), category.getImage(), category.getDate_creation(), category.getState(), category.isType_executive(), category.getRestaurant()));
                        if (position == 0) {
                            ID_CATEGORY = category.getId();
                            Log.i("Lino", "ID_CATEGORIA: " + ID_CATEGORY);
                            //fillOutTheProducts();
                            recoverExecutiveProductsByApis(ID_CATEGORY);
                        }
                        position++;
                    }


                    AdapterExecutiveCategories adapterExecutiveCategories = new AdapterExecutiveCategories(categories, getApplicationContext());
                    adapterExecutiveCategories.setListener(new AdapterExecutiveCategories.CategoryListener() {
                        @Override
                        public void categorySelected(int idCategory) {
                            Toast.makeText(getApplicationContext(), "ID Category: " + idCategory, Toast.LENGTH_SHORT).show();
                            ID_CATEGORY = idCategory;
                            //por medi de este metodo, le notifico al recycler view que hubo uncmabio, or lo tanto se vuelve a ejecutar el on bind view holder
                            adapterExecutiveCategories.notifyDataSetChanged();
                            //fillOutTheProducts();
                            recoverExecutiveProductsByApis(ID_CATEGORY);

                        }
                    });
                    recyclerViewCategories.setAdapter(adapterExecutiveCategories);
                    //fillOutTheProducts();


                } else {
                    Log.i("Lino", "The response wasn't successful. Code: " + response.code());


                }
                //stopProgressDialogCategory();


            }

            @Override
            public void onFailure(Call<List<CategoriesResponse>> call, Throwable t) {
                Log.i("Lino", "OnFailure! > " + t.getMessage());
                //stopProgressDialogCategory();
            }
        });


    }
    private void startProgressDialogProduct() {
        progDailogProducts = new ProgressDialog(Executive_Category.this);
        progDailogProducts.setMessage(getResources().getString(R.string.loading));
        //progDailog.setIndeterminate(false);
        progDailogProducts.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progDailog.setCancelable(true);
        progDailogProducts.show();
    }

    private void stopProgressDialogProduct() {
        progDailogProducts.dismiss();

    }

    private void recoverExecutiveProductsByApis(int idCategoria) {
        products = new ArrayList<>();
        startProgressDialogProduct();
        Log.i("productsExecutive", "ID_RESTAURANT: " + ID_RESTAURANT);
        Call<List<ProductResponse>> call = RetrofitClient.getInstance(Executive_Category.this).getApi().getProductsByExecutiveCategoryAndRestaurant(ID_RESTAURANT, idCategoria);
        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                Log.i("productsExecutive", "I'm inside OnResponse FillOutTheProduct");
                if (response.isSuccessful()) {
                    Log.i("productsExecutive", "The response was successful. Code: " + response.code());
                    List<ProductResponse> productsByCategoryResponse = response.body();
                    for (ProductResponse product : productsByCategoryResponse) {
                        Log.i("productsExecutive", product.toString());
                        products.add(product);
                    }
                    AdapterExecutiveProducts adapterExecutiveProducts = new AdapterExecutiveProducts(products, Executive_Category.this);
                    recyclerViewProducts.setAdapter(adapterExecutiveProducts);
                } else {
                    Log.i("productsExecutive", "The response wasn't successful. Code: " + response.code());
                }

                stopProgressDialogProduct();

            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                Log.i("productsExecutive", "The response wasn't successful. Problem: " + t.getMessage());
                stopProgressDialogProduct();

            }
        });

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

    private boolean therIsProductFromTheSameCategory(String idProduct) {
        boolean therIsProductFromTheSameCategory = false;
        for (SelectProduct selectProduct : list_selected_products) {
            if (selectProduct.getId() == Integer.parseInt(idProduct)) {
                therIsProductFromTheSameCategory = true;
            }
        }
        return therIsProductFromTheSameCategory;
    }

    private SelectProduct getProductById(String idProduct) {
        SelectProduct objProduct = null;
        for (ProductResponse product : products) {
            if ((product.getId() == Integer.parseInt(idProduct))) {
                objProduct = new SelectProduct(product.getId(), product.getName());
                break;
            }
        }
        return objProduct;
    }
}