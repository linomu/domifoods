package com.unicauca.domifoods.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.unicauca.domifoods.R;

public class AlertFragment  {

    //alert dialog alert fragment
    public AlertFragment(Context contexto){
        final Dialog dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.fragment_alerta_exit);
    }
}
