package com.example.priscilla.inspectores;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;


public class  dialog_consulta extends DialogFragment{

    public dialog_consulta() {
        // Required empty public constructor
    }

    public static dialog_consulta newInstance(int title) {
        dialog_consulta frag = new dialog_consulta();
        Bundle args = new Bundle();
        args.putInt("title", title);
        //args.putString(title, "title");
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage("Desea tomar una foto?")
                .setIcon(R.drawable.ic_action_camera)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((ConsultaInfraccion)getActivity()).doCameraPositiveClick();
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((ConsultaInfraccion)getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();
    }


}