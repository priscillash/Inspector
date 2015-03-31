package com.example.priscilla.inspectores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;



public class fragment_consultaResultado extends Fragment {


    private String matricula;
    private Boolean multa;
    private String dateTimeConsulta;
    private String dateTimeEndTicket;
    private Button botonFoto;


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
        // Required empty public constructor
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
        TextView vehiculo_matricula = (TextView) view.findViewById(R.id.vehiculo_matricula);
        vehiculo_matricula.setText("Matrícula: " + matricula);

        TextView dateConsulta = (TextView)view.findViewById(R.id.DateTimeConsulta);
        dateConsulta.setText(dateTimeConsulta);


        if (!multa) {
            //No correspoonde infracción
            TextView vehiculo_infraccion = (TextView) view.findViewById(R.id.vehiculo_infraccion);
            vehiculo_infraccion.setText("No corresponde infracción");


        }else{
            //Corresponde infracción!!!
            TextView vehiculo_infraccion = (TextView) view.findViewById(R.id.vehiculo_infraccion);
            vehiculo_infraccion.setText("Vehículo en Infracción!");

            //set the properties for button
            botonFoto = new Button(getActivity());
            botonFoto.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            botonFoto.setText("FOTO");

            //add button to the layout

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
