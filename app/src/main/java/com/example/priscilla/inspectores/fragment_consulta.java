package com.example.priscilla.inspectores;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.plus.PlusOneButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fragment_consulta extends Fragment  {

    public String letras, numeros;

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

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final EditText etNumeros = (EditText)getView().findViewById(R.id.Numeros);
        final EditText etLetras = (EditText)getView().findViewById(R.id.Letras);


        ImageButton unsearchButton = (ImageButton) getView().findViewById(R.id.search_button);

        unsearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se setean los parametros del usuario para validar
                letras = etLetras.getText().toString();
                numeros = etNumeros.getText().toString();
                String matricula[]= {letras, numeros};
                ((ConsultaInfraccion)getActivity()).consulta(letras,numeros);

            }
        });

    }

}
