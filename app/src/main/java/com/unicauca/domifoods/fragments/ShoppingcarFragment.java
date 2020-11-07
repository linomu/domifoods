package com.unicauca.domifoods.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.unicauca.domifoods.CallBacks.MyItemTouchHelperCallback;
import com.unicauca.domifoods.interfaces.CallBackItemTouch;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.adapters.AdapterListProducts;
import com.unicauca.domifoods.adapters.AdapterRestaurants;
import com.unicauca.domifoods.modelsProduct.ProductShoppingCart;

import java.util.ArrayList;
import java.util.List;


public class ShoppingcarFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView menu_options;
    NavController navController;
    public static ArrayList<ProductShoppingCart> products = new ArrayList<>();
    private AdapterListProducts adapterListProducts;
    RecyclerView recyclerView, recyclerView_products;
    TextView sum, title;
    RelativeLayout layout;

    public static double sumTotal;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppingcarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingcarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingcarFragment newInstance(String param1, String param2) {
        ShoppingcarFragment fragment = new ShoppingcarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shoppingcar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        //sum = view.findViewById(R.id.total_compra);
        menu_options = view.findViewById(R.id.menu_options_nav);
        menu_options.setOnNavigationItemSelectedListener(this);
        Menu menu = menu_options.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);
        sum= view.findViewById(R.id.total_compra);
        recyclerView = view.findViewById(R.id.RecyclerCar);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapterListProducts = new AdapterListProducts(products, this.getContext());
        adapterListProducts.setProducts(products);
        adapterListProducts.prueba(this);

        //borrar item David
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        //---
        recyclerView.setAdapter(adapterListProducts);
        title = view.findViewById(R.id.tv_title);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_menu:
                navController.navigate(R.id.action_shoppingcarFragment_to_restaurantFragment);
                break;
            case R.id.nav_shopping_car:
                Toast.makeText(getContext(), "Here we are :)", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_order:
                navController.navigate(R.id.action_shoppingcarFragment_to_ordersFragment);
                break;
            case R.id.nav_deliveryman:
                navController.navigate(R.id.action_shoppingcarFragment_to_delivermanFragment);
                break;
        }
        return true;
    }


    @Override
    public void onStart() {
        super.onStart();
        sumTotal = 0;
        Menu menu = menu_options.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);
        Log.e("Lino", "OnStart ShoppingFragment");
        this.getActivity().getWindow().getDecorView().setSystemUiVisibility(
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


    /*
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
   */
}