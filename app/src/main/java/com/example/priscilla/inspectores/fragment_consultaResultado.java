package com.example.priscilla.inspectores;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;


public class fragment_consultaResultado extends Fragment {


    private String matricula;
    private Boolean multa;
    private String dateTimeConsulta;
    private String dateTimeEndTicket;
    private ImageButton botonFoto;


    public static fragment_consultaResultado newInstance(String param1, Boolean param2, String param3, String param4) {
        fragment_consultaResultado fragment = new fragment_consultaResultado();
        Bundle args = new Bundle();
        args.putString("matricula", param1);
        args.putBoolean("multa", param2);
        args.putString("dateTimeConsulta",param3);
        args.putString("dateTimeEndTicket",param4);
        fragment.setArguments(args);
        return fragment;
    }

    public fragment_consultaResultado() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            matricula = getArguments().getString("matricula");
            multa = getArguments().getBoolean("multa");
            dateTimeConsulta = getArguments().getString("dateTimeConsulta");
            dateTimeEndTicket = getArguments().getString("dateTimeEndTicket");

        }

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_consulta_resultado, container, false);

        TextView unvehiculo_matricula = (TextView) view.findViewById(R.id.unvehiculo_matricula);
        unvehiculo_matricula.setText(matricula);
        unvehiculo_matricula.setTextColor(getResources().getColor(R.color.black));

        if (!multa) {
            //No correspoonde infracción
            TextView vehiculo_infraccion = (TextView) view.findViewById(R.id.vehiculo_infraccion);
            vehiculo_infraccion.setText("Vehículo con ticket vigente");
            vehiculo_infraccion.setTextColor(getResources().getColor(R.color.verde));
        }else{
            //Corresponde infracción
            TextView vehiculo_infraccion = (TextView) view.findViewById(R.id.vehiculo_infraccion);
            vehiculo_infraccion.setText("Vehículo en Infracción");
            vehiculo_infraccion.setTextColor(getResources().getColor(R.color.rojo));
            //seteo de propiedades al botón
            botonFoto = new ImageButton(getActivity());
            botonFoto.setImageDrawable(getResources().getDrawable(R.drawable.foto_icon));
            botonFoto.setBackgroundColor(Color.WHITE);
            botonFoto.setScaleX(1);
            botonFoto.setScaleY(1);
            LinearLayout ll = (LinearLayout)view.findViewById(R.id.foto);
            ll.addView(botonFoto);

        }
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (multa) {
            botonFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openIntent = new Intent(getActivity(), Camera_Activity.class);
                    getActivity().startActivity(openIntent);

                }
            });
        }
    }
}
