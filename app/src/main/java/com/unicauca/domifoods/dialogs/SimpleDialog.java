package com.unicauca.domifoods.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.unicauca.domifoods.R;

public class SimpleDialog extends DialogFragment {
    //attributes
    private String mensaje_from_server;

    @NonNull
    @Override
    //builder  iniciliate 
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView =inflater.inflate(R.layout.dialog_mensaje, null);
        TextView txt_userName = dialogView.findViewById(R.id.tv_mensaje_servidor);
        txt_userName.setText(getMensaje_from_server());
        builder.setView(dialogView);
        return builder.create();
    }

    public String getMensaje_from_server() {
        return mensaje_from_server;
    }

    public void setMensaje_from_server(String mensaje_from_server) {
        this.mensaje_from_server = mensaje_from_server;
    }
}
