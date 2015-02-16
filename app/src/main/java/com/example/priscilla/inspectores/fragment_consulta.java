package com.example.priscilla.inspectores;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.plus.PlusOneButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fragment_consulta extends Fragment {

    String letras, numeros;
    public fragment_consulta() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_consulta, container, false);

        //se define la estructura de datos que identifica al Usuario.
        final EditText etNumeros = (EditText)view.findViewById(R.id.Numeros);
        final EditText etLetras = (EditText)view.findViewById(R.id.Letras);
        ImageButton unsearchButton = (ImageButton)view.findViewById(R.id.sch_button);
        unsearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se setean los parametros del usuario para validar
                letras = etLetras.getText().toString();
                numeros = etNumeros.getText().toString();
                String matricula[]={letras,numeros};
                consulta(matricula);

            }
        });
        return view;
    }

    public void consulta(String matricula[]){

        letras = matricula[0];
        numeros = matricula[1];

        if (letras.equals("HHH") && numeros.equals("1111")) {
            showDialogConsulta();
        }
    }

    public void showDialogConsulta() {
        dialog_consulta newFragment = dialog_consulta.newInstance(R.string.Vehiculo_en_Infraccion);
        newFragment.show(getActivity().getFragmentManager(), "dialog");
    }

    public void doPositiveClick() {

    }

    public void doNegativeClick() {

    }


}
