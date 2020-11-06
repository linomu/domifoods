package com.unicauca.domifoods.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unicauca.domifoods.R;
import com.unicauca.domifoods.domain.Product;
import com.unicauca.domifoods.modelsProduct.ProductShoppingCart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailProductsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tv_precio,tv_description;
    private Button btn_add,btn_minus,btn_add_shopping_cart;
    Product product;
    private int cant;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailProductsFragment newInstance(String param1, String param2) {
        DetailProductsFragment fragment = new DetailProductsFragment();
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
        View view= inflater.inflate(R.layout.fragment_detail_products, container, false);
        tv_precio = view.findViewById(R.id.tv_precio);
        tv_description = view.findViewById(R.id.tv_description);
        btn_add = view.findViewById(R.id.btn_add);
        btn_add_shopping_cart = view.findViewById(R.id.btn_add_shopping_cart);
        btn_minus = view.findViewById(R.id.btn_minus);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cant=1;
        if(getArguments()!=null){
            DetailProductsFragmentArgs args = DetailProductsFragmentArgs.fromBundle(getArguments());
            product = args.getProduct();
            Log.i("lino", "Desde DetailProductosFragment "+product.toString());
        }

        tv_precio.setText(String.valueOf(product.getPrice()));
        tv_description.setText(product.getDescription());
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cant++;
                tv_precio.setText(String.valueOf(product.getPrice()*cant));
            }
        });
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cant--;
                tv_precio.setText(String.valueOf(product.getPrice()*cant));
            }
        });
        btn_add_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductShoppingCart productShoppingCart
                        = new ProductShoppingCart(product.getId(), cant, product.getImage(), product.getName(), product.getPrice(), product.getPrice()*cant);
                ShoppingcarFragment.products.add(productShoppingCart);
                Toast.makeText(getContext(), "Producto Agregado! 😁", Toast.LENGTH_SHORT).show();
            }
        });


    }
}