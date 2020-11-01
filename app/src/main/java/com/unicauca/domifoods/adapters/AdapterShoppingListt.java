package com.unicauca.domifoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.unicauca.domifoods.R;
import com.unicauca.domifoods.modelsProduct.ProductShoppingCart;

import java.util.List;

public class AdapterShoppingListt extends ArrayAdapter<ProductShoppingCart> {

    public AdapterShoppingListt(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View result = convertView;
        if (result == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.fragment_shoppingcar, null);
        }
        /*CheckBox checkbox = (CheckBox) result.findViewById(R.id.fragment_shoppingcar);
        ProductShoppingCart item = getItem(position);
        checkbox.setText(item.getText());
        checkbox.setChecked(item.isChecked());
        */return result;
    }

}
