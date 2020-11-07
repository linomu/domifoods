package com.unicauca.domifoods.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.unicauca.domifoods.CallBacks.MyItemTouchHelperCallback;
import com.unicauca.domifoods.MainActivity;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.adapters.AdapterListProducts;
import com.unicauca.domifoods.interfaces.CallBackItemTouch;
import com.unicauca.domifoods.modelsProduct.ProductShoppingCart;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity implements CallBackItemTouch, BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView menu_options;
    NavController navController;
    public static ArrayList<ProductShoppingCart> products = new ArrayList<>();
    private AdapterListProducts adapterListProducts;
    RecyclerView recyclerView, recyclerView_products;
    TextView sum, title;
    RelativeLayout layout;

    public static double sumTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        ObtenerVariables();
    }

    private void ObtenerVariables() {

        layout = findViewById(R.id.layout_main_activity);
        //navController = Navigation.findNavController(R.id.nav_host_fragment_container);
        //sum = view.findViewById(R.id.total_compra);
        menu_options = findViewById(R.id.menu_options_nav);
        menu_options.setOnNavigationItemSelectedListener(this);
        Menu menu = menu_options.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);
        sum= findViewById(R.id.total_compra);
        recyclerView = findViewById(R.id.RecyclerCar);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterListProducts = new AdapterListProducts(products, this);
        adapterListProducts.setProducts(products);
        adapterListProducts.prueba(this);

        //borrar item David
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        //---
        recyclerView.setAdapter(adapterListProducts);
        title = findViewById(R.id.tv_title);
    }

    @Override
    public void onStart() {
        super.onStart();
        sumTotal = 0;
        Menu menu = menu_options.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);
        Log.e("Lino", "OnStart ShoppingFragment");
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        Log.i("lino", "Productos que están en el carrito");
        //List<ProductShoppingCart> listProducts = new ArrayList<>();
        for(ProductShoppingCart productShoppingCart : products){
            Log.i("lino", productShoppingCart.toString());
            sumTotal = sumTotal + productShoppingCart.getSubtotal();
        }

        sum.setText("Total: $"+sumTotal);

    }


    public void etiquetado(double text){
        if(text > 0) {
            title.setText("");
            sum.setText("Total: $" + text);
        }
    }


    @Override
    public void itemTouchOnMode(int oldPosition, int newPosition) {
        products.add(newPosition,products.remove(oldPosition));
        adapterListProducts.notifyItemMoved(oldPosition,newPosition);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        String nombre = products.get(viewHolder.getAdapterPosition()).getName();
        final ProductShoppingCart deleteItem = products.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();
        adapterListProducts.removeItem(viewHolder.getAdapterPosition());
        Snackbar snackbar = Snackbar.make(layout, nombre +"=>Eliminado", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("CANCELAR", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListProducts.restoreItem(deleteItem,deletedIndex);
            }
        });
        snackbar.setActionTextColor(Color.GREEN);
        snackbar.show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_menu:
                //navController.navigate(R.id.action_productsFragment_to_restaurantFragment);
                break;
            case R.id.nav_shopping_car:
                //navController.navigate(R.id.action_productsFragment_to_shoppingcarFragment);

                break;
            case R.id.nav_order:
                //navController.navigate(R.id.action_productsFragment_to_ordersFragment);
                break;
            case R.id.nav_deliveryman:
                //navController.navigate(R.id.action_productsFragment_to_delivermanFragment);
                break;
        }
        return true;
    }
}