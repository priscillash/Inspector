package com.example.priscilla.inspectores;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class fragment_consultaResultado extends Fragment {


    private String mletras;
    private String mnumeros;


    public static fragment_consultaResultado newInstance(String param1, String param2) {
        fragment_consultaResultado fragment = new fragment_consultaResultado();
        Bundle args = new Bundle();
        args.putString("letras", param1);
        args.putString("numeros", param2);
        fragment.setArguments(args);
        return fragment;
    }

    public fragment_consultaResultado() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mletras = getArguments().getString("letras");
            mnumeros = getArguments().getString("numeros");
        }

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_consulta_resultado, container, false);
        TextView vehiculo_matricula = (TextView) view.findViewById(R.id.vehiculo_matricula);
        vehiculo_matricula.setText("Matrícula: ");

        if (mletras.equals("HHH") && mnumeros.equals("1111")) {
            //No correspoonde infracción
            TextView vehiculo_infraccion = (TextView) view.findViewById(R.id.vehiculo_infraccion);
            vehiculo_infraccion.setText("No corresponde infracción");
        }else{
            //Corresponde infracción!!!
            TextView vehiculo_infraccion = (TextView) view.findViewById(R.id.vehiculo_infraccion);
            vehiculo_infraccion.setText("Vehículo en Infracción!");
        }

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
