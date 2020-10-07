package com.unicauca.domifoods.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.unicauca.domifoods.R;

public class DialogIpHost extends DialogFragment {

    private String ip_host;
    private NoticeDialogListener listener;

    public String getIp_host() {
        return ip_host;
    }

    public void setIp_host(String ip_host) {
        this.ip_host = ip_host;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView =inflater.inflate(R.layout.dialog_iphost, null);

        final EditText et_ip_host  = dialogView.findViewById(R.id.et_ip_host);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        setIp_host(et_ip_host.getText().toString());
                        Toast.makeText(getContext(), "ip: "+et_ip_host.getText().toString(), Toast.LENGTH_SHORT).show();
                        listener.onDialogPositiveClick(DialogIpHost.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //DialogLogin.this.getDialog().cancel();
                        listener.onDialogNegativeClick(DialogIpHost.this);
                    }
                });
        return builder.create();

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }

    }
}
